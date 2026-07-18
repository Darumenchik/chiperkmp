package com.chiper.kz.components.chat

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.canvas.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icons.Icons
import androidx.compose.material.Icons.filled.Refresh
import androidx.compose.material.Icons.filled.ArrowDownward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawCircle
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chiper.kz.theme.glass.GlassSurface
import com.chiper.kz.theme.glass.GlassElevation
import com.chiper.kz.theme.glass.GlassShapes
import kotlin.math.min
import kotlin.math.PI
import kotlin.math.sin
import kotlinx.coroutines.delay

@Composable
fun PullToRefresh(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    var pullDistance by remember { mutableStateOf(0f) }
    val maxPullDistance = 120f
    val triggerDistance = 80f

    Box(modifier = modifier.fillMaxSize()) {
        // Refresh indicator
        AnimatedVisibility(
            visible = pullDistance > 0 || isRefreshing,
            enter = fadeIn(animationSpec = tween(200)) + expandVertically(animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)),
            exit = fadeOut(animationSpec = tween(150)) + shrinkVertically(animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(pullDistance.coerceAtMost(triggerDistance).dp)
                    .padding(top = -pullDistance.coerceAtMost(triggerDistance).dp)
                    .wrapContentSize(Alignment.TopCenter),
                contentAlignment = Alignment.TopCenter
            ) {
                GlassSurface(
                    modifier = Modifier
                        .size(60.dp)
                        .padding(top = 20.dp)
                        .graphicsLayer {
                            scaleX = (pullDistance / triggerDistance).coerceIn(0f, 1f)
                            scaleY = (pullDistance / triggerDistance).coerceIn(0f, 1f)
                        },
                    shape = GlassShapes.Circle,
                    elevation = GlassElevation.Floating
                ) {
                    if (isRefreshing) {
                        InfiniteSpinner(
                            modifier = Modifier.size(40.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        val rotation = (pullDistance / triggerDistance * 180).coerceIn(0f, 180f)
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .graphicsLayer { rotationZ = rotation }
                                .wrapContentSize(Alignment.Center),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowDownward,
                                contentDescription = "Pull to refresh",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }

        // Content with pull gesture
        PullToRefreshBox(
            refreshing = isRefreshing,
            onRefresh = onRefresh,
            modifier = Modifier.fillMaxSize()
        ) { state ->
            content()
        }
    }
}

@Composable
fun InfiniteSpinner(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Dp = 3.dp
) {
    val progress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "spinner"
    )

    Canvas(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.Transparent)
    ) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val radius = min(size.width, size.height) / 2f - strokeWidth.toPx() / 2f
        val startAngle = progress * 360
        val sweepAngle = 90f + (sin((progress * 720).toDouble() * PI / 180.0) + 1) * 90f

        drawArc(
            color = color,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = Offset(center.x - radius, center.y - radius),
            size = Size(2 * radius, 2 * radius),
            style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Composable
fun RefreshHeader(
    isRefreshing: Boolean,
    pullDistance: Float,
    triggerDistance: Float = 80f,
    modifier: Modifier = Modifier
) {
    val progress = (pullDistance / triggerDistance).coerceIn(0f, 1f)
    val infiniteTransition = rememberInfiniteTransition(label = "refresh_rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(1000, easing = LinearEasing), RepeatMode.Restart),
        label = "rotation"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(triggerDistance.dp)
            .padding(top = -triggerDistance.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        AnimatedVisibility(visible = pullDistance > 0 || isRefreshing) {
            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .wrapContentSize(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Animated refresh icon
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .graphicsLayer {
                            scaleX = progress
                            scaleY = progress
                            rotationZ = if (isRefreshing) rotation else progress * 180f
                        }
                        .wrapContentSize(Alignment.Center),
                    contentAlignment = Alignment.Center
                ) {
                    if (isRefreshing) {
                        InfiniteSpinner(color = MaterialTheme.colorScheme.primary)
                    } else {
                        Icon(
                            imageVector = Icons.Filled.ArrowDownward,
                            contentDescription = "Pull to refresh",
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = progress)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = when {
                        isRefreshing -> "Обновление..."
                        pullDistance >= triggerDistance -> "Отпустите для обновления"
                        else -> "Потяните для обновления"
                    },
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = progress),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}