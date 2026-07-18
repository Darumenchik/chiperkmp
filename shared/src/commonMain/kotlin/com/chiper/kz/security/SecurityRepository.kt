package com.chiper.kz.security

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SecurityRepository {

    private val _isLocked = MutableStateFlow(true)
    val isLocked: StateFlow<Boolean> = _isLocked.asStateFlow()

    private val _passcodeEnabled = MutableStateFlow(false)
    val passcodeEnabled: StateFlow<Boolean> = _passcodeEnabled.asStateFlow()

    private val _biometricEnabled = MutableStateFlow(false)
    val biometricEnabled: StateFlow<Boolean> = _biometricEnabled.asStateFlow()

    private val _autoLockTimeout = MutableStateFlow(300000L)
    val autoLockTimeout: StateFlow<Long> = _autoLockTimeout.asStateFlow()

    private var storedPasscode: String? = null

    suspend fun setPasscode(passcode: String) {
        storedPasscode = passcode
        _passcodeEnabled.value = true
    }

    suspend fun removePasscode() {
        storedPasscode = null
        _passcodeEnabled.value = false
    }

    suspend fun verifyPasscode(passcode: String): Boolean {
        return storedPasscode != null && storedPasscode == passcode
    }

    suspend fun setBiometricEnabled(enabled: Boolean) {
        _biometricEnabled.value = enabled
    }

    suspend fun getPasscodeEnabled(): Boolean = _passcodeEnabled.value

    suspend fun getBiometricEnabled(): Boolean = _biometricEnabled.value

    suspend fun incrementFailedAttempts() {}

    suspend fun getFailedAttempts(): Int = 0

    suspend fun lockOut(durationMs: Long) {}

    suspend fun isLockedOut(): Boolean = false

    suspend fun setAutoLockTimeout(timeoutMs: Long) {
        _autoLockTimeout.value = timeoutMs
    }

    suspend fun updateLastActive() {}

    suspend fun shouldAutoLock(): Boolean = false

    companion object {
        fun create(): SecurityRepository = SecurityRepository()
    }
}
