package com.example.frontendtdm.screens

import android.annotation.SuppressLint
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
import androidx.navigation.NavHostController
import com.example.frontendtdm.R

@SuppressLint("InvalidColorHexValue")
@Composable
fun SignUpScreen(navController: NavHostController) {
    var fullName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

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
                onValueChange = { fullName = it },
                placeholder = { Text("Enter full name") },
                modifier = Modifier.fillMaxWidth()
            )

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
                onValueChange = { phoneNumber = it },
                placeholder = { Text("Enter your phone number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )

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
                onValueChange = { email = it },
                placeholder = { Text("Enter email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

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
                onValueChange = { password = it },
                placeholder = { Text("Enter password") },
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
                onValueChange = { confirmPassword = it },
                placeholder = { Text("Enter password") },
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

            Spacer(modifier = Modifier.height(24.dp))

            var signUpButtonColor by remember { mutableStateOf(Color.Gray) }

            // Sign Up Button
            Button(
                onClick = { signUpButtonColor = Color.Black },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = buttonColors(containerColor = signUpButtonColor)
            ) {
                Text("Sign up")
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