package com.example.masakin.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.example.masakin.viewmodel.RegisterViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun RegisterScreen(
    onRegisterClick: (username: String, email: String, password: String) -> Unit = { _, _, _ -> },
    onBackToLoginClick: () -> Unit = {},
    onFacebookClick: () -> Unit = {},
    onGoogleClick: () -> Unit = {},
    onAppleClick: () -> Unit = {},
    viewModel: RegisterViewModel = viewModel()
) {
    // --- collect state dari ViewModel
    val username by viewModel.username.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val showPassword by viewModel.showPassword.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header image + fade
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

            Text("Register", color = Red50, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(
                "Hello! Provide the following information to complete the registration process.",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Spacer(Modifier.height(24.dp))

            // Username
            OutlinedTextField(
                value = username,
                onValueChange = viewModel::updateUsername,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Username", color = Grey40, fontSize = 12.sp) },
                leadingIcon = { Icon(Icons.Filled.Person, null, tint = Grey40) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                textStyle = LocalTextStyle.current.copy(fontSize = 12.sp),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFE0E0E0),
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(Modifier.height(12.dp))

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = viewModel::updateEmail,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Email", color = Grey40, fontSize = 12.sp) },
                leadingIcon = { Icon(Icons.Filled.Email, null, tint = Grey40) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                textStyle = LocalTextStyle.current.copy(fontSize = 12.sp),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFE0E0E0),
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(Modifier.height(12.dp))

            // Password
            OutlinedTextField(
                value = password,
                onValueChange = viewModel::updatePassword,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Password", color = Grey40, fontSize = 12.sp) },
                leadingIcon = { Icon(Icons.Filled.Lock, null, tint = Grey40) },
                singleLine = true,
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                textStyle = LocalTextStyle.current.copy(fontSize = 12.sp),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFE0E0E0),
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(Modifier.height(8.dp))

            // Show password
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = showPassword,
                    onCheckedChange = { viewModel.toggleShowPassword() },
                    colors = CheckboxDefaults.colors(checkedColor = Red50, uncheckedColor = Grey40)
                )
                Text("Show Password", fontSize = 12.sp, color = Grey40)
            }

            Spacer(Modifier.height(16.dp))

            // Register
            Button(
                onClick = {
                    viewModel.register()
                    onRegisterClick(username, email, password)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(6.dp, RoundedCornerShape(28.dp)),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Red50)
            ) {
                Text("Register", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(24.dp))

            // OR divider
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(Modifier.weight(1f), color = Color(0xFFE0E0E0))
                Text("OR", modifier = Modifier.padding(horizontal = 16.dp), fontSize = 12.sp, color = Grey40)
                Divider(Modifier.weight(1f), color = Color(0xFFE0E0E0))
            }

            Spacer(Modifier.height(20.dp))

            // Socials (logo Google sedikit lebih besar)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                SocialLoginButton(R.drawable.ic_facebook, "Login with Facebook", onClick = onFacebookClick, iconSize = 44.dp)
                Spacer(Modifier.width(16.dp))
                SocialLoginButton(R.drawable.ic_google, "Login with Google", onClick = onGoogleClick, iconSize = 44.dp)
                Spacer(Modifier.width(16.dp))
                SocialLoginButton(R.drawable.ic_apple, "Login with Apple", onClick = onAppleClick, iconSize = 44.dp)
            }

            Spacer(Modifier.height(20.dp))

            // Back to Login
            val annotated = buildAnnotatedString {
                withStyle(SpanStyle(color = Grey40)) { append("Already have an account? ") }
                withStyle(SpanStyle(color = Red50, fontWeight = FontWeight.Bold)) { append("Login") }
            }
            TextButton(
                onClick = onBackToLoginClick,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(annotated, textAlign = TextAlign.Center)
            }
        }
    }
}
