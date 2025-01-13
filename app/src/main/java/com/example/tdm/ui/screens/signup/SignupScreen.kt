package com.example.tdm.ui.screens.signup

import com.example.tdm.R
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tdm.data.repository.AuthRepository

@Composable
fun SignUpScreen(
    repository: AuthRepository,
    navController: NavHostController,
    context: Context,
) {
    val factory = SignUpViewModelFactory(repository)
    val viewModel: SignUpViewModel = viewModel(factory = factory)

    var fullName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var fullNameError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }
    val isLoading by viewModel.isLoading.collectAsState()
    val signUpSuccess by viewModel.signUpSuccess.collectAsState()
    val signUpError by viewModel.signUpError.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF6F6F6)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Back Arrow and Title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                Text(
                    text = "Sign Up",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
                        fontSize = 20.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Full Name Field
            Text(
                text = "Full name",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
                    fontSize = 14.sp,
                    color = Color.Black
                ),
                modifier = Modifier.align(Alignment.Start)
            )
            OutlinedTextField(
                value = fullName,
                onValueChange = {
                    fullName = it
                    fullNameError = it.isEmpty()
                },
                placeholder = { Text("Enter full name") },
                isError = fullNameError,
                modifier = Modifier.fillMaxWidth()
            )
            if (fullNameError) {
                Text(
                    text = "Full name cannot be empty",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Phone Number Field
            Text(
                text = "Phone number",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
                    fontSize = 14.sp,
                    color = Color.Black
                ),
                modifier = Modifier.align(Alignment.Start)
            )
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = {
                    phoneNumber = it
                    phoneError = it.isEmpty()
                },
                placeholder = { Text("Enter your phone number") },
                isError = phoneError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )
            if (phoneError) {
                Text(
                    text = "Phone number cannot be empty",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Email Field
            Text(
                text = "Email",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
                    fontSize = 14.sp,
                    color = Color.Black
                ),
                modifier = Modifier.align(Alignment.Start)
            )
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = it.isEmpty()
                },
                placeholder = { Text("Enter email") },
                isError = emailError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )
            if (emailError) {
                Text(
                    text = "Email cannot be empty",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Password Field
            Text(
                text = "Password",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
                    fontSize = 14.sp,
                    color = Color.Black
                ),
                modifier = Modifier.align(Alignment.Start)
            )
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = it.isEmpty()
                },
                placeholder = { Text("Enter password") },
                isError = passwordError,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(id = if (passwordVisible) R.drawable.eyeslash else R.drawable.eye),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            if (passwordError) {
                Text(
                    text = "Password cannot be empty",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Confirm Password Field
            Text(
                text = "Confirm Password",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
                    fontSize = 14.sp,
                    color = Color.Black
                ),
                modifier = Modifier.align(Alignment.Start)
            )
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    confirmPasswordError = it.isEmpty() || it != password
                },
                placeholder = { Text("Enter password") },
                isError = confirmPasswordError,
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            painter = painterResource(id = if (confirmPasswordVisible) R.drawable.eyeslash else R.drawable.eye),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            if (confirmPasswordError) {
                Text(
                    text = "Passwords do not match",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Sign Up Button
            Button(
                onClick = {
                    fullNameError = fullName.isEmpty()
                    phoneError = phoneNumber.isEmpty()
                    emailError = email.isEmpty()
                    passwordError = password.isEmpty()
                    confirmPasswordError = confirmPassword.isEmpty() || confirmPassword != password

                    if (!fullNameError && !phoneError && !emailError && !passwordError && !confirmPasswordError) {
                        viewModel.signUp(context, fullName, phoneNumber, email, password)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = buttonColors(containerColor = if (isLoading) Color.LightGray else Color.Black)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Sign up")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Check for success
            if (signUpSuccess) {
                LaunchedEffect(Unit) {
                    navController.navigate("home") {
                        popUpTo(0) // Supprime les écrans précédents
                    }
                }
            }

            // Display Error
            if (signUpError != null) {
                Text(
                    text = signUpError ?: "",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // OR Divider
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                HorizontalDivider(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp),
                    color = Color.Gray
                )
                Text(
                    text = "or",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                        fontSize = 14.sp,
                        color = Color.Gray
                    ),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                HorizontalDivider(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp),
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Google Sign Up Button
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                colors = buttonColors(containerColor = Color(0xFFF87146))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icongoogle),
                    contentDescription = "Google Icon",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 8.dp)
                )
                Text("Continue with Google", color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Already have an account? Log in
            Row {
                Text(
                    text = "Already have an account? ",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                )
                Text(
                    text = "Log in",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                        fontSize = 14.sp,
                        color = Color(0xFFF87146)
                    ),
                    modifier = Modifier.clickable { navController.navigate("login") }
                )
            }
        }
    }
}
