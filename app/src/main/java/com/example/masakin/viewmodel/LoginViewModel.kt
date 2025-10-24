package com.example.masakin.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {
    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _showPassword = MutableStateFlow(false)

    val email = _email.asStateFlow()
    val password = _password.asStateFlow()
    val showPassword = _showPassword.asStateFlow()

    fun updateEmail(v: String) { _email.value = v }
    fun updatePassword(v: String) { _password.value = v }
    fun toggleShowPassword() { _showPassword.value = !_showPassword.value }

    fun login() {
        // TODO: implement autentikasi
    }
}
