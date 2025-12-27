package com.example.smartpark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onGoogleLoginClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    var emailOrPhone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {

        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = "Welcome Back",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Sign in to continue parking smart",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = emailOrPhone,
            onValueChange = { emailOrPhone = it },
            placeholder = { Text("Email or Phone") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = onForgotPasswordClick,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                text = "Forgot password?",
                color = Color(0xFF2196F3),
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3)
            )
        ) {
            Text("Login", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Or continue with",
            fontSize = 13.sp,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onGoogleLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Login with Google", color = Color.Black)
        }

        // 🔽 Push Sign-Up to Bottom
        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don't have an account?",
                fontSize = 13.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.width(4.dp))
            TextButton(onClick = onSignUpClick) {
                Text(
                    text = "Sign up",
                    fontSize = 13.sp,
                    color = Color(0xFF2196F3),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
