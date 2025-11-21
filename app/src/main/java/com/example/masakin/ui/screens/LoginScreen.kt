package com.example.masakin.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.masakin.R
import com.example.masakin.ui.components.SocialLoginButton
import com.example.masakin.ui.theme.Grey40
import com.example.masakin.ui.theme.Red50
import com.example.masakin.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    onLoginClick: (email: String, password: String) -> Unit = { _, _ -> },
    onForgotPasswordClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    onFacebookClick: () -> Unit = {},
    onGoogleClick: () -> Unit = {},
    onAppleClick: () -> Unit = {},
    viewModel: LoginViewModel = viewModel()
) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val showPassword by viewModel.showPassword.collectAsState()

    val emailError by viewModel.emailError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()

    val isLoading by viewModel.isLoading.collectAsState()
    val isSuccess by viewModel.isSuccess.collectAsState()

    val context = LocalContext.current

    // sukses login → toast + callback ke atas
    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            Toast.makeText(
                context,
                "Anda telah berhasil login",
                Toast.LENGTH_SHORT
            ).show()
            onLoginClick(email, password)
        }
    }

    // error global → toast
    LaunchedEffect(Unit) {
        viewModel.errorMessage.collect { msg ->
            Toast.makeText(
                context,
                msg,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header image + fade to white
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .align(Alignment.TopCenter)
        ) {
            Image(
                painter = painterResource(id = R.drawable.loginimage),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            0f to Color.Transparent,
                            0.7f to Color.White.copy(alpha = 0.85f),
                            1f to Color.White
                        )
                    )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(Modifier.height(160.dp))

            Text(
                text = "Login",
                color = Red50,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Welcome! enter your login credentials to continue.",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Spacer(Modifier.height(24.dp))

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = viewModel::updateEmail,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Email", color = Grey40, fontSize = 12.sp) },
                leadingIcon = {
                    Icon(Icons.Filled.Email, contentDescription = null, tint = Grey40)
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                textStyle = LocalTextStyle.current.copy(fontSize = 12.sp),
                shape = RoundedCornerShape(16.dp),
                isError = emailError != null,
                supportingText = {
                    if (emailError != null) {
                        Text(
                            text = emailError ?: "",
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 10.sp
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFE0E0E0),
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    errorBorderColor = MaterialTheme.colorScheme.error,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    errorContainerColor = Color.White
                )
            )

            Spacer(Modifier.height(12.dp))

            // Password
            OutlinedTextField(
                value = password,
                onValueChange = viewModel::updatePassword,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Password", color = Grey40, fontSize = 12.sp) },
                leadingIcon = {
                    Icon(Icons.Filled.Lock, contentDescription = null, tint = Grey40)
                },
                singleLine = true,
                visualTransformation = if (showPassword) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                textStyle = LocalTextStyle.current.copy(fontSize = 12.sp),
                shape = RoundedCornerShape(16.dp),
                isError = passwordError != null,
                supportingText = {
                    if (passwordError != null) {
                        Text(
                            text = passwordError ?: "",
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 10.sp
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFE0E0E0),
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    errorBorderColor = MaterialTheme.colorScheme.error,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    errorContainerColor = Color.White
                )
            )

            Spacer(Modifier.height(8.dp))

            // Show password + Forgot
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = showPassword,
                        onCheckedChange = { viewModel.toggleShowPassword() },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Red50,
                            uncheckedColor = Grey40
                        )
                    )
                    Text("Show Password", fontSize = 12.sp, color = Grey40)
                }

                TextButton(onClick = onForgotPasswordClick) {
                    Text("Forgot Password?", fontSize = 12.sp, color = Grey40)
                }
            }

            Spacer(Modifier.height(16.dp))

            // Login button
            Button(
                onClick = { viewModel.login() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(6.dp, RoundedCornerShape(28.dp)),
                shape = RoundedCornerShape(28.dp),
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = Red50)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text("Login", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(24.dp))

            // OR divider
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(Modifier.weight(1f), color = Color(0xFFE0E0E0))
                Text(
                    "OR",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    fontSize = 12.sp,
                    color = Grey40
                )
                Divider(Modifier.weight(1f), color = Color(0xFFE0E0E0))
            }

            Spacer(Modifier.height(20.dp))

            // Socials
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                SocialLoginButton(
                    icon = R.drawable.ic_facebook,
                    contentDescription = "Login with Facebook",
                    onClick = onFacebookClick,
                    iconSize = 44.dp
                )
                Spacer(Modifier.width(16.dp))
                SocialLoginButton(
                    icon = R.drawable.ic_google,
                    contentDescription = "Login with Google",
                    onClick = onGoogleClick,
                    iconSize = 44.dp
                )
                Spacer(Modifier.width(16.dp))
                SocialLoginButton(
                    icon = R.drawable.ic_apple,
                    contentDescription = "Login with Apple",
                    onClick = onAppleClick,
                    iconSize = 44.dp
                )
            }

            Spacer(Modifier.height(20.dp))

            // Register
            val annotated = buildAnnotatedString {
                withStyle(SpanStyle(color = Grey40)) { append("Don't have account? ") }
                withStyle(
                    SpanStyle(
                        color = Red50,
                        fontWeight = FontWeight.Bold
                    )
                ) { append("Register") }
            }
            TextButton(
                onClick = onRegisterClick,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(annotated, textAlign = TextAlign.Center)
            }
        }
    }
}
