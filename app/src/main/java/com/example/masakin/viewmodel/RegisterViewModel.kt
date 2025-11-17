package com.example.masakin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _username = MutableStateFlow("")
    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _showPassword = MutableStateFlow(false)

    // UI state
    private val _isLoading = MutableStateFlow(false)
    private val _isSuccess = MutableStateFlow(false)
    private val _errorMessage = MutableSharedFlow<String>() // one-off events

    // Expose as read-only
    val username = _username.asStateFlow()
    val email = _email.asStateFlow()
    val password = _password.asStateFlow()
    val showPassword = _showPassword.asStateFlow()

    val isLoading = _isLoading.asStateFlow()
    val isSuccess = _isSuccess.asStateFlow()
    val errorMessage = _errorMessage.asSharedFlow()

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun updateUsername(v: String) { _username.value = v }
    fun updateEmail(v: String) { _email.value = v }
    fun updatePassword(v: String) { _password.value = v }
    fun toggleShowPassword() { _showPassword.value = !_showPassword.value }

    fun register() {
        val emailVal = _email.value.trim()
        val passwordVal = _password.value
        val usernameVal = _username.value.trim()

        if (emailVal.isEmpty() || passwordVal.isEmpty() || usernameVal.isEmpty()) {
            viewModelScope.launch { _errorMessage.emit("Username, email, dan password wajib diisi.") }
            return
        }

        _isLoading.value = true
        _isSuccess.value = false

        auth.createUserWithEmailAndPassword(emailVal, passwordVal)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    // update display name (username)
                    val user = auth.currentUser
                    if (user != null) {
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(usernameVal)
                            .build()
                        user.updateProfile(profileUpdates)
                            .addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    _isSuccess.value = true
                                } else {
                                    _isSuccess.value = true // akun sudah dibuat; displayName gagal tidak fatal
                                    viewModelScope.launch {
                                        _errorMessage.emit("Akun dibuat tapi gagal set username: ${updateTask.exception?.message}")
                                    }
                                }
                            }
                    } else {
                        // seharusnya tidak terjadi
                        _isSuccess.value = true
                    }
                } else {
                    viewModelScope.launch {
                        _errorMessage.emit(task.exception?.message ?: "Gagal membuat akun.")
                    }
                }
            }
    }
}
