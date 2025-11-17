package com.example.masakin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _showPassword = MutableStateFlow(false)

    // UI state
    private val _isLoading = MutableStateFlow(false)
    private val _isSuccess = MutableStateFlow(false)
    private val _errorMessage = MutableSharedFlow<String>() // one-off events

    val email = _email.asStateFlow()
    val password = _password.asStateFlow()
    val showPassword = _showPassword.asStateFlow()

    val isLoading = _isLoading.asStateFlow()
    val isSuccess = _isSuccess.asStateFlow()
    val errorMessage = _errorMessage.asSharedFlow()

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun updateEmail(v: String) { _email.value = v }
    fun updatePassword(v: String) { _password.value = v }
    fun toggleShowPassword() { _showPassword.value = !_showPassword.value }

    fun login() {
        val emailVal = _email.value.trim()
        val passwordVal = _password.value

        if (emailVal.isEmpty() || passwordVal.isEmpty()) {
            viewModelScope.launch { _errorMessage.emit("Email dan password wajib diisi.") }
            return
        }

        _isLoading.value = true
        _isSuccess.value = false

        auth.signInWithEmailAndPassword(emailVal, passwordVal)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    _isSuccess.value = true
                } else {
                    viewModelScope.launch {
                        _errorMessage.emit(task.exception?.message ?: "Login gagal.")
                    }
                }
            }
    }

    /** optional: logout helper */
    fun logout() {
        auth.signOut()
        _isSuccess.value = false
    }
}
