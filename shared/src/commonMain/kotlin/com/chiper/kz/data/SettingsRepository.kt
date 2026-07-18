package com.chiper.kz.data

import com.chiper.kz.theme.AppTheme
import com.chiper.kz.theme.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsRepository {

    private val _appTheme = MutableStateFlow(AppTheme.Default)
    val appTheme: StateFlow<AppTheme> = _appTheme.asStateFlow()

    private val _themeMode = MutableStateFlow<ThemeMode>(ThemeMode.System)
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    private val _user = MutableStateFlow(AppUser())
    val user: StateFlow<AppUser> = _user.asStateFlow()

    fun getAppTheme(): AppTheme = _appTheme.value
    fun getThemeMode(): ThemeMode = _themeMode.value

    suspend fun setAppTheme(theme: AppTheme) {
        _appTheme.value = theme
    }

    suspend fun setThemeMode(mode: ThemeMode) {
        _themeMode.value = mode
    }

    suspend fun setUser(user: AppUser?) {
        if (user != null) _user.value = user
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
