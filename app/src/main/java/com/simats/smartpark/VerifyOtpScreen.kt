package com.simats.smartpark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun VerifyOtpScreen(
    onBackClick: () -> Unit,
    onVerifyClick: (String, String) -> Unit, // (email, otp)
    email: String = ""
) {
    var otp1 by remember { mutableStateOf("") }
    var otp2 by remember { mutableStateOf("") }
    var otp3 by remember { mutableStateOf("") }
    var otp4 by remember { mutableStateOf("") }
    var otp5 by remember { mutableStateOf("") }
    var otp6 by remember { mutableStateOf("") }
    
    val focusRequester1 = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }
    val focusRequester3 = remember { FocusRequester() }
    val focusRequester4 = remember { FocusRequester() }
    val focusRequester5 = remember { FocusRequester() }
    val focusRequester6 = remember { FocusRequester() }
    
    // Combine OTP digits
    val otp = remember(otp1, otp2, otp3, otp4, otp5, otp6) {
        otp1 + otp2 + otp3 + otp4 + otp5 + otp6
    }

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
            text = "Verify OTP",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Enter the 6-digit code sent to your email",
            fontSize = 14.sp,
            color = Color.Gray
        )

        if (email.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = email,
                fontSize = 12.sp,
                color = Color(0xFF2196F3),
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Six OTP input boxes
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OtpBox(
                value = otp1,
                onValueChange = { newValue ->
                    if (newValue.length <= 1) {
                        otp1 = newValue
                        if (newValue.isNotEmpty()) {
                            focusRequester2.requestFocus()
                        }
                    }
                },
                focusRequester = focusRequester1,
                onBackspace = { /* Focus stays on first box */ }
            )
            
            OtpBox(
                value = otp2,
                onValueChange = { newValue ->
                    if (newValue.length <= 1) {
                        otp2 = newValue
                        if (newValue.isNotEmpty()) {
                            focusRequester3.requestFocus()
                        } else {
                            focusRequester1.requestFocus()
                        }
                    }
                },
                focusRequester = focusRequester2,
                onBackspace = {
                    if (otp2.isEmpty()) {
                        focusRequester1.requestFocus()
                    }
                }
            )
            
            OtpBox(
                value = otp3,
                onValueChange = { newValue ->
                    if (newValue.length <= 1) {
                        otp3 = newValue
                        if (newValue.isNotEmpty()) {
                            focusRequester4.requestFocus()
                        } else {
                            focusRequester2.requestFocus()
                        }
                    }
                },
                focusRequester = focusRequester3,
                onBackspace = {
                    if (otp3.isEmpty()) {
                        focusRequester2.requestFocus()
                    }
                }
            )
            
            OtpBox(
                value = otp4,
                onValueChange = { newValue ->
                    if (newValue.length <= 1) {
                        otp4 = newValue
                        if (newValue.isNotEmpty()) {
                            focusRequester5.requestFocus()
                        } else {
                            focusRequester3.requestFocus()
                        }
                    }
                },
                focusRequester = focusRequester4,
                onBackspace = {
                    if (otp4.isEmpty()) {
                        focusRequester3.requestFocus()
                    }
                }
            )
            
            OtpBox(
                value = otp5,
                onValueChange = { newValue ->
                    if (newValue.length <= 1) {
                        otp5 = newValue
                        if (newValue.isNotEmpty()) {
                            focusRequester6.requestFocus()
                        } else {
                            focusRequester4.requestFocus()
                        }
                    }
                },
                focusRequester = focusRequester5,
                onBackspace = {
                    if (otp5.isEmpty()) {
                        focusRequester4.requestFocus()
                    }
                }
            )
            
            OtpBox(
                value = otp6,
                onValueChange = { newValue ->
                    if (newValue.length <= 1) {
                        otp6 = newValue
                        if (newValue.isEmpty()) {
                            focusRequester5.requestFocus()
                        }
                    }
                },
                focusRequester = focusRequester6,
                onBackspace = {
                    if (otp6.isEmpty()) {
                        focusRequester5.requestFocus()
                    }
                }
            )
        }
        
        LaunchedEffect(Unit) {
            focusRequester1.requestFocus()
        }

        Spacer(modifier = Modifier.height(32.dp))

        var isLoading by remember { mutableStateOf(false) }
        var errorMessage by remember { mutableStateOf<String?>(null) }
        val scope = rememberCoroutineScope()

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

        Button(
            onClick = {
                if (email.isEmpty()) {
                    errorMessage = "Email not found. Please go back and try again."
                    return@Button
                }

                if (otp.length != 6) {
                    errorMessage = "Please enter 6-digit OTP"
                    return@Button
                }

                isLoading = true
                errorMessage = null

                scope.launch {
                    try {
                        val response = com.simats.smartpark.api.ApiClient.apiService.verifyOtp(
                            com.simats.smartpark.model.VerifyOtpRequest(
                                email = email,
                                otp = otp
                            )
                        )

                        if (response.isSuccessful) {
                            val body = response.body()
                            if (body != null && body.status == "success") {
                                // OTP verified, proceed to reset password
                                onVerifyClick(email, otp)
                            } else {
                                errorMessage = body?.message ?: "Invalid OTP"
                            }
                        } else {
                            val errorBody = response.errorBody()?.string()
                            errorMessage = "Error: ${response.code()}. ${errorBody ?: "Unknown error"}"
                        }
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
            enabled = otp.length == 6 && !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Verify", color = Color.White, fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Didn't receive code?", fontSize = 13.sp, color = Color.Gray)
            TextButton(onClick = { /* Resend Logic */ }) {
                Text(text = "Resend", color = Color(0xFF2196F3), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun OtpBox(
    value: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
    onBackspace: () -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            // Only allow digits
            if (newValue.all { it.isDigit() }) {
                if (newValue.length <= 1) {
                    onValueChange(newValue)
                } else if (newValue.length > 1) {
                    // Handle paste - take only first digit
                    onValueChange(newValue.take(1))
                }
            } else if (newValue.isEmpty()) {
                // Handle backspace
                onValueChange("")
                onBackspace()
            }
        },
        modifier = Modifier
            .width(48.dp)
            .height(56.dp)
            .focusRequester(focusRequester),
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        ),
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF2196F3),
            unfocusedBorderColor = Color(0xFFE0E0E0)
        )
    )
}
