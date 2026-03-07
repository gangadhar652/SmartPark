package com.simats.smartpark

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.smartpark.api.ApiClient
import com.simats.smartpark.model.LoginRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    val context = LocalContext.current
    val sharedPrefs = remember { context.getSharedPreferences("SmartParkPrefs", Context.MODE_PRIVATE) }
    
    // Load remembered email on screen load
    var email by remember { 
        mutableStateOf(
            sharedPrefs.getString("remembered_email", "") ?: ""
        )
    }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { 
        mutableStateOf(
            sharedPrefs.getBoolean("remember_me", false)
        )
    }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(60.dp))

        /* 🅿️ APP ICON */
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color(0xFF2196F3), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.LocalParking,
                contentDescription = "SmartPark",
                tint = Color.White,
                modifier = Modifier.size(56.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        /* 📱 APP TITLE */
        Text(
            text = "SmartPark",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(40.dp))

        /* 📧 EMAIL FIELD */
        Text(
            text = "Email",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Email") },
            leadingIcon = {
                Icon(
                    Icons.Default.Email,
                    contentDescription = "Email",
                    tint = Color.Black
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF2196F3),
                unfocusedBorderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        /* 🔐 PASSWORD FIELD */
        Text(
            text = "Password",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Password") },
            leadingIcon = {
                Icon(
                    Icons.Default.Lock,
                    contentDescription = "Password",
                    tint = Color.Black
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF2196F3),
                unfocusedBorderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        /* ✅ REMEMBER ME & FORGOT PASSWORD */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = { 
                        rememberMe = it
                        // Save remember me preference
                        sharedPrefs.edit().putBoolean("remember_me", it).apply()
                        // If unchecked, clear remembered email
                        if (!it) {
                            sharedPrefs.edit().remove("remembered_email").apply()
                        }
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF4CAF50),
                        uncheckedColor = Color.Gray
                    )
                )
                Text(
                    text = "Remember Me",
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
            TextButton(onClick = onForgotPasswordClick) {
                Text(
                    text = "Forgot Password?",
                    color = Color(0xFF2196F3), // Blue
                    fontSize = 13.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Success message (shown below password field)
        successMessage?.let { success ->
            Text(
                text = success,
                color = Color(0xFF4CAF50),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
        }

        // Error message
        errorMessage?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        /* ✅ LOGIN BUTTON */
        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    errorMessage = "Please fill in all fields"
                    return@Button
                }
                
                isLoading = true
                errorMessage = null
                successMessage = null
                
                scope.launch {
                    try {
                        val response = ApiClient.apiService.login(
                            LoginRequest(email = email.trim(), password = password)
                        )
                        
                        if (response.isSuccessful) {
                            val body = response.body()
                            if (body != null && body.status == "success") {
                                // Store user email in SharedPreferences
                                sharedPrefs.edit().putString("user_email", email.trim()).apply()
                                
                                // If Remember Me is checked, save the email
                                if (rememberMe) {
                                    sharedPrefs.edit()
                                        .putString("remembered_email", email.trim())
                                        .putBoolean("remember_me", true)
                                        .apply()
                                } else {
                                    // If unchecked, clear remembered email
                                    sharedPrefs.edit()
                                        .remove("remembered_email")
                                        .putBoolean("remember_me", false)
                                        .apply()
                                }
                                
                                isLoading = false
                                errorMessage = null
                                successMessage = "Login successful!"
                                // Wait a moment to show the message, then navigate
                                delay(1500)
                                successMessage = null
                                onLoginClick()
                            } else {
                                isLoading = false
                                successMessage = null
                                errorMessage = body?.message ?: response.message() ?: "Login failed. Please try again."
                            }
                        } else {
                            isLoading = false
                            successMessage = null
                            errorMessage = "Server error (${response.code()}): ${response.message()}"
                        }
                    } catch (e: java.net.UnknownHostException) {
                        isLoading = false
                        successMessage = null
                        errorMessage = "Cannot connect to server.\n\nPlease check:\n1. XAMPP Apache is RUNNING\n2. Test in browser: http://localhost/smartpark/\n3. Files in C:\\xampp\\htdocs\\smartpark\\"
                    } catch (e: java.net.SocketTimeoutException) {
                        isLoading = false
                        successMessage = null
                        errorMessage = "Connection timeout after 30 seconds.\n\nTroubleshooting:\n1. XAMPP Apache is running?\n2. Test: http://localhost/smartpark/login.php\n3. Check firewall settings\n4. For physical device: Use your PC's IP in ApiClient.kt"
                    } catch (e: java.net.ConnectException) {
                        isLoading = false
                        successMessage = null
                        errorMessage = "Connection refused.\n\nCheck:\n1. XAMPP Apache is STARTED\n2. Port 80 is not blocked\n3. Test: http://localhost/smartpark/"
                    } catch (e: Exception) {
                        isLoading = false
                        successMessage = null
                        val errorMsg = e.localizedMessage ?: e.message ?: "Unknown error"
                        errorMessage = "Error: $errorMsg\n\nEnsure XAMPP Apache is running and test in browser first."
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50) // Green button like in the image
            ),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    "Login",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
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
