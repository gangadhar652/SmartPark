package com.example.smartpark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SignUpScreen(
    onSignUpClick: () -> Unit,
    onLoginClick: () -> Unit
) {

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }          // ✅ EMAIL
    var phone by remember { mutableStateOf("") }
    var vehicleNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Create Account",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Join Smart Park today",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        /* 👤 FULL NAME */
        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            placeholder = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        /* 📧 EMAIL */
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Email Address") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        /* 📞 PHONE */
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            placeholder = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        /* 🚗 VEHICLE */
        OutlinedTextField(
            value = vehicleNumber,
            onValueChange = { vehicleNumber = it },
            placeholder = { Text("Vehicle Number (Optional)") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        /* 🔐 PASSWORD */
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Create Password") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            visualTransformation = if (showPassword)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        imageVector = if (showPassword)
                            Icons.Default.Visibility
                        else
                            Icons.Default.VisibilityOff,
                        contentDescription = "Toggle password"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        /* 🔐 CONFIRM PASSWORD */
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholder = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            visualTransformation = if (showConfirmPassword)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { showConfirmPassword = !showConfirmPassword }) {
                    Icon(
                        imageVector = if (showConfirmPassword)
                            Icons.Default.Visibility
                        else
                            Icons.Default.VisibilityOff,
                        contentDescription = "Toggle confirm password"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        /* ✅ SIGN UP BUTTON */
        Button(
            onClick = onSignUpClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3)
            )
        ) {
            Text("Sign Up", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.weight(1f))

        /* 🔁 LOGIN LINK */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Already have an account?",
                fontSize = 13.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.width(4.dp))
            TextButton(onClick = onLoginClick) {
                Text(
                    text = "Login",
                    fontSize = 13.sp,
                    color = Color(0xFF2196F3),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
