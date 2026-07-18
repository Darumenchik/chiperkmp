package com.chiper.kz.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chiper.kz.data.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class ThemeViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _themeMode = MutableStateFlow(settingsRepository.getThemeMode())
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    private val _appTheme = MutableStateFlow(settingsRepository.getAppTheme())
    val appTheme: StateFlow<AppTheme> = _appTheme.asStateFlow()

    private val _isAnimating = MutableStateFlow(false)
    val isAnimating: StateFlow<Boolean> = _isAnimating.asStateFlow()

    fun setThemeMode(mode: ThemeMode) {
        _themeMode.value = mode
        settingsRepository.setThemeMode(mode)
    }

    fun setAppTheme(theme: AppTheme) {
        _isAnimating.value = true
        _appTheme.value = theme
        settingsRepository.setAppTheme(theme)
        
        viewModelScope.launch {
            delay(500)
            _isAnimating.value = false
        }
    }
}

sealed interface ThemeMode {
    data object Light : ThemeMode
    data object Dark : ThemeMode
    data object System : ThemeMode
}