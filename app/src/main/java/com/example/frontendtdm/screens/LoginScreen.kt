package com.example.frontendtdm.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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

@Composable
fun LoginScreen(navController: NavHostController) {
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logoappfr),
                contentDescription = "Login Logo",
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 20.dp)
            )

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
                label = { Text("Enter your phone number") },
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
                label = { Text("Enter password ") },
                isError = passwordError,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisible) R.drawable.eyeslash else R.drawable.eye
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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

            Spacer(modifier = Modifier.height(24.dp))

            var loginButtonColor by remember { mutableStateOf(Color.Gray) }

            Button(
                onClick = {
                    phoneError = phoneNumber.isEmpty()
                    passwordError = password.isEmpty()
                    if (!phoneError && !passwordError) {
                        navController.navigate("home")
                    } else {
                        loginButtonColor = Color.Black
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = buttonColors(containerColor = loginButtonColor)
            ) {
                Text("Sign In")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                HorizontalDivider(modifier = Modifier.weight(1f))
                Text("or", modifier = Modifier.padding(horizontal = 8.dp))
                HorizontalDivider(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))

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

            TextButton(onClick = { navController.navigate("signup") }) {
                Row {
                    Text(
                        text = "Don't have an account? ",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    )
                    Text(
                        text = "Sign Up",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                            fontSize = 14.sp,
                            color = Color(0xFFF87146)
                        )
                    )
                }
            }
        }
    }
}
