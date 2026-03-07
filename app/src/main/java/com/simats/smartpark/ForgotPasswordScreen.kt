package com.simats.smartpark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.smartpark.api.ApiClient
import com.simats.smartpark.model.ForgotPasswordRequest
import kotlinx.coroutines.launch

@Composable
fun ForgotPasswordScreen(
    onBackClick: () -> Unit,
    onContinueClick: (String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        IconButton(onClick = onBackClick) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Forgot Password?",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Enter your email to reset your password",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { 
                email = it
                errorMessage = null
                successMessage = null
            },
            placeholder = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Error message
        errorMessage?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Success message
        successMessage?.let { success ->
            Text(
                text = success,
                color = Color(0xFF4CAF50),
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Validate email
                if (email.isEmpty()) {
                    errorMessage = "Please enter your email"
                    return@Button
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    errorMessage = "Please enter a valid email address"
                    return@Button
                }

                isLoading = true
                errorMessage = null
                successMessage = null

                scope.launch {
                    try {
                        val response = ApiClient.apiService.forgotPassword(
                            ForgotPasswordRequest(email = email.trim())
                        )

                        if (response.isSuccessful) {
                            val body = response.body()
                            if (body != null && body.status == "success") {
                                successMessage = body.message
                                // Show OTP if available (development mode)
                                if (!body.otp.isNullOrEmpty()) {
                                    successMessage = "${body.message}\n\nOTP: ${body.otp}"
                                }
                                // Navigate to OTP screen after a short delay
                                kotlinx.coroutines.delay(1000)
                                onContinueClick(email.trim())
                            } else {
                                errorMessage = body?.message ?: "Failed to send OTP"
                            }
                        } else {
                            try {
                                val errorBody = response.errorBody()?.string()
                                // Try to parse as JSON to get error message
                                if (errorBody != null && errorBody.startsWith("{")) {
                                    try {
                                        val errorJson = com.google.gson.Gson().fromJson(errorBody, com.google.gson.JsonObject::class.java)
                                        errorMessage = errorJson.get("message")?.asString ?: errorBody
                                    } catch (e: Exception) {
                                        errorMessage = "Error ${response.code()}: $errorBody"
                                    }
                                } else {
                                    errorMessage = "Error ${response.code()}: ${errorBody ?: "Unknown error"}"
                                }
                            } catch (e: Exception) {
                                errorMessage = "Error ${response.code()}: ${e.message}"
                            }
                        }
                    } catch (e: com.google.gson.JsonSyntaxException) {
                        errorMessage = "Invalid response format. Please check server configuration."
                    } catch (e: java.net.UnknownHostException) {
                        errorMessage = "Cannot connect to server. Check XAMPP is running."
                    } catch (e: Exception) {
                        errorMessage = "Error: ${e.localizedMessage ?: e.message}"
                    } finally {
                        isLoading = false
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3)
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
                Text("Continue", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}
