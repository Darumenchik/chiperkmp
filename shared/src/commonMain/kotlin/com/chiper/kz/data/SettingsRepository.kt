package com.chiper.kz.theme

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SettingsRepository(private val dataStore: DataStore<Preferences>) {

    private val THEME_ID_KEY = preferencesKey<String>("theme_id")
    private val THEME_MODE_KEY = preferencesKey<String>("theme_mode")
    private val USER_ID_KEY = preferencesKey<String>("user_id")
    private val USER_NAME_KEY = preferencesKey<String>("user_name")
    private val USER_EMAIL_KEY = preferencesKey<String>("user_email")
    private val NOTIFICATIONS_ENABLED_KEY = preferencesKey<Boolean>("notifications_enabled")
    private val AUTO_DOWNLOAD_MEDIA_KEY = preferencesKey<Boolean>("auto_download_media")
    private val LANGUAGE_KEY = preferencesKey<String>("language")

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
                val prefs = dataStore.data.firstOrNull().await()
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
        dataStore.edit { prefs ->
            prefs[THEME_ID_KEY] = theme.id
        }
    }

    suspend fun setThemeMode(mode: ThemeMode) {
        _themeMode.value = mode
        dataStore.edit { prefs ->
            prefs[THEME_MODE_KEY] = mode.name
        }
    }

    suspend fun setUser(user: User?) {
        user?.let {
            dataStore.edit { prefs ->
                prefs[USER_ID_KEY] = it.id
                prefs[USER_NAME_KEY] = it.name
                prefs[USER_EMAIL_KEY] = it.email
            }
        } ?: dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = null
            prefs[USER_NAME_KEY] = null
            prefs[USER_EMAIL_KEY] = null
        }
    }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[NOTIFICATIONS_ENABLED_KEY] = enabled
        }
    }

    suspend fun setAutoDownloadMedia(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[AUTO_DOWNLOAD_MEDIA_KEY] = enabled
        }
    }

    suspend fun setLanguage(language: String) {
        dataStore.edit { prefs ->
            prefs[LANGUAGE_KEY] = language
        }
    }
}

@kotlinx.serialization.Serializable
data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val avatarUrl: String = "",
    val bio: String = ""
)