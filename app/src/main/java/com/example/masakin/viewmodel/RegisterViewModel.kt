package com.example.masakin.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegisterViewModel : ViewModel() {
    private val _username = MutableStateFlow("")
    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _showPassword = MutableStateFlow(false)

    val username = _username.asStateFlow()
    val email = _email.asStateFlow()
    val password = _password.asStateFlow()
    val showPassword = _showPassword.asStateFlow()

    fun updateUsername(v: String) { _username.value = v }
    fun updateEmail(v: String) { _email.value = v }
    fun updatePassword(v: String) { _password.value = v }
    fun toggleShowPassword() { _showPassword.value = !_showPassword.value }

    fun register() {
        // TODO: call repository / API using username.value, email.value, password.value
    }
}
