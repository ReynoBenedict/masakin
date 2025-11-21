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

    // form value
    private val _username = MutableStateFlow("")
    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _showPassword = MutableStateFlow(false)

    // error per kolom
    private val _usernameError = MutableStateFlow<String?>(null)
    private val _emailError = MutableStateFlow<String?>(null)
    private val _passwordError = MutableStateFlow<String?>(null)

    // ui state umum
    private val _isLoading = MutableStateFlow(false)
    private val _isSuccess = MutableStateFlow(false)
    private val _errorMessage = MutableSharedFlow<String>()   // untuk toast global

    // expose sebagai read only
    val username = _username.asStateFlow()
    val email = _email.asStateFlow()
    val password = _password.asStateFlow()
    val showPassword = _showPassword.asStateFlow()

    val usernameError = _usernameError.asStateFlow()
    val emailError = _emailError.asStateFlow()
    val passwordError = _passwordError.asStateFlow()

    val isLoading = _isLoading.asStateFlow()
    val isSuccess = _isSuccess.asStateFlow()
    val errorMessage = _errorMessage.asSharedFlow()

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun updateUsername(v: String) {
        _username.value = v
        _usernameError.value = null
    }

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

    fun register() {
        val emailVal = _email.value.trim()
        val passwordVal = _password.value
        val usernameVal = _username.value.trim()

        // reset error dulu
        _usernameError.value = null
        _emailError.value = null
        _passwordError.value = null

        var hasError = false

        if (usernameVal.isEmpty()) {
            _usernameError.value = "Kolom tidak boleh kosong"
            hasError = true
        }

        if (emailVal.isEmpty()) {
            _emailError.value = "Kolom tidak boleh kosong"
            hasError = true
        }

        if (passwordVal.isEmpty()) {
            _passwordError.value = "Kolom tidak boleh kosong"
            hasError = true
        } else if (passwordVal.length < 6) {
            _passwordError.value = "Password minimal enam karakter"
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

        auth.createUserWithEmailAndPassword(emailVal, passwordVal)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
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
                                    _isSuccess.value = true   // akun sudah dibuat, gagal set display name tidak fatal
                                    viewModelScope.launch {
                                        _errorMessage.emit(
                                            "Akun dibuat tapi gagal set username: ${updateTask.exception?.message}"
                                        )
                                    }
                                }
                            }
                    } else {
                        // harusnya jarang terjadi
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
