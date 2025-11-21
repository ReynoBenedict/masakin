package com.example.masakin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _showPassword = MutableStateFlow(false)

    // error per kolom
    private val _emailError = MutableStateFlow<String?>(null)
    private val _passwordError = MutableStateFlow<String?>(null)

    // UI state
    private val _isLoading = MutableStateFlow(false)
    private val _isSuccess = MutableStateFlow(false)
    private val _errorMessage = MutableSharedFlow<String>() // one-off events

    val email = _email.asStateFlow()
    val password = _password.asStateFlow()
    val showPassword = _showPassword.asStateFlow()

    val emailError = _emailError.asStateFlow()
    val passwordError = _passwordError.asStateFlow()

    val isLoading = _isLoading.asStateFlow()
    val isSuccess = _isSuccess.asStateFlow()
    val errorMessage = _errorMessage.asSharedFlow()

    private val auth: FirebaseAuth = Firebase.auth

    fun updateEmail(v: String) {
        _email.value = v
        _emailError.value = null
    }

    fun updatePassword(v: String) {
        _password.value = v
        _passwordError.value = null
    }

    fun toggleShowPassword() {
        _showPassword.value = !_showPassword.value
    }

    fun login() {
        val emailVal = _email.value.trim()
        val passwordVal = _password.value

        // reset error dulu
        _emailError.value = null
        _passwordError.value = null

        var hasError = false

        if (emailVal.isEmpty()) {
            _emailError.value = "Kolom tidak boleh kosong"
            hasError = true
        }

        if (passwordVal.isEmpty()) {
            _passwordError.value = "Kolom tidak boleh kosong"
            hasError = true
        }

        if (hasError) {
            viewModelScope.launch {
                _errorMessage.emit("Periksa kembali kolom yang ditandai merah")
            }
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
                    val msg = "Maaf email/password anda salah, silahkan coba kembali"
                    _emailError.value = msg
                    _passwordError.value = msg
                    viewModelScope.launch {
                        _errorMessage.emit(msg)
                    }
                }
            }
    }

    fun logout() {
        auth.signOut()
        _isSuccess.value = false
    }
}
