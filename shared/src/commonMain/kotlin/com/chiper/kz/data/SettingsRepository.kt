package com.chiper.kz.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsRepository {

    private val _appTheme = MutableStateFlow(AppTheme.Default)
    val appTheme: StateFlow<AppTheme> = _appTheme.asStateFlow()

    private val _themeMode = MutableStateFlow(ThemeMode.System)
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val prefs = dataStore.data.firstOrNull()
                prefs?.let {
                    val themeId = it[THEME_ID_KEY] ?: AppTheme.Default.id
                    val modeStr = it[THEME_MODE_KEY] ?: ThemeMode.System.name
                    _appTheme.value = AppTheme.fromId(themeId)
                    _themeMode.value = ThemeMode.valueOf(modeStr)
                }
            } catch (e: Exception) {
                _appTheme.value = AppTheme.Default
                _themeMode.value = ThemeMode.System
            }
        }
    }

    fun getAppTheme(): AppTheme = _appTheme.value
    fun getThemeMode(): ThemeMode = _themeMode.value

    suspend fun setAppTheme(theme: AppTheme) {
        _appTheme.value = theme
    }

    suspend fun setThemeMode(mode: ThemeMode) {
        _themeMode.value = mode
    }

    suspend fun setUser(user: AppUser?) {
        // In-memory only; persist in platform layer
    }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
    }

    suspend fun setAutoDownloadMedia(enabled: Boolean) {
    }

    suspend fun setLanguage(language: String) {
    }

    companion object {
        fun create(): SettingsRepository = SettingsRepository()
    }
}

data class AppUser(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val avatarUrl: String = "",
    val bio: String = ""
)