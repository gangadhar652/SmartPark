package com.simats.smartpark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.smartpark.api.ApiClient
import com.simats.smartpark.model.SignupRequest
import kotlinx.coroutines.launch

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
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

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
            leadingIcon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Full Name",
                    tint = Color(0xFF9C27B0) // Purple
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        /* 📧 EMAIL */
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Email Address") },
            leadingIcon = {
                Icon(
                    Icons.Default.Email,
                    contentDescription = "Email",
                    tint = Color(0xFFE91E63) // Pink/Red
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        /* 📞 PHONE */
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            placeholder = { Text("Phone Number") },
            leadingIcon = {
                Icon(
                    Icons.Default.Phone,
                    contentDescription = "Phone",
                    tint = Color(0xFF4CAF50) // Green
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        /* 🚗 VEHICLE */
        OutlinedTextField(
            value = vehicleNumber,
            onValueChange = { vehicleNumber = it },
            placeholder = { Text("Vehicle Number") },
            leadingIcon = {
                Icon(
                    Icons.Default.DirectionsCar,
                    contentDescription = "Vehicle Number",
                    tint = Color(0xFFFF9800) // Orange
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        /* 🔐 PASSWORD */
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Create Password") },
            leadingIcon = {
                Icon(
                    Icons.Default.Lock,
                    contentDescription = "Password",
                    tint = Color(0xFFF44336) // Red
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
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
            leadingIcon = {
                Icon(
                    Icons.Default.Lock,
                    contentDescription = "Confirm Password",
                    tint = Color(0xFFF44336) // Red (same as password)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
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

        /* ✅ SIGN UP BUTTON */
        Button(
            onClick = {
                // Validation
                if (fullName.isBlank() || email.isBlank() || phone.isBlank() || password.isBlank()) {
                    errorMessage = "Please fill in all required fields"
                    return@Button
                }
                
                if (password != confirmPassword) {
                    errorMessage = "Passwords do not match"
                    return@Button
                }
                
                if (password.length < 6) {
                    errorMessage = "Password must be at least 6 characters"
                    return@Button
                }
                
                isLoading = true
                errorMessage = null
                
                scope.launch {
                    try {
                        val response = ApiClient.apiService.signup(
                            SignupRequest(
                                full_name = fullName.trim(),
                                email = email.trim(),
                                phone = phone.trim(),
                                vehicle_number = vehicleNumber.trim().ifEmpty { "" },
                                password = password
                            )
                        )
                        
                        if (response.isSuccessful) {
                            val body = response.body()
                            if (body != null && body.status == "success") {
                                // Store user email in SharedPreferences
                                val sharedPrefs = context.getSharedPreferences("SmartParkPrefs", android.content.Context.MODE_PRIVATE)
                                sharedPrefs.edit().putString("user_email", email.trim()).apply()
                                
                                isLoading = false
                                onSignUpClick()
                            } else {
                                isLoading = false
                                errorMessage = body?.message ?: response.message() ?: "Signup failed. Please try again."
                            }
                        } else {
                            isLoading = false
                            errorMessage = "Server error (${response.code()}): ${response.message()}"
                        }
                    } catch (e: java.net.UnknownHostException) {
                        isLoading = false
                        errorMessage = "Cannot connect to server.\n\nPlease check:\n1. XAMPP Apache is RUNNING\n2. Test in browser: http://localhost/smartpark/\n3. Files in C:\\xampp\\htdocs\\smartpark\\"
                    } catch (e: java.net.SocketTimeoutException) {
                        isLoading = false
                        errorMessage = "Connection timeout after 30 seconds.\n\nTroubleshooting:\n1. XAMPP Apache is running?\n2. Test: http://localhost/smartpark/signup.php\n3. Check firewall settings\n4. For physical device: Use your PC's IP in ApiClient.kt"
                    } catch (e: java.net.ConnectException) {
                        isLoading = false
                        errorMessage = "Connection refused.\n\nCheck:\n1. XAMPP Apache is STARTED\n2. Port 80 is not blocked\n3. Test: http://localhost/smartpark/"
                    } catch (e: Exception) {
                        isLoading = false
                        val errorMsg = e.localizedMessage ?: e.message ?: "Unknown error"
                        errorMessage = "Error: $errorMsg\n\nEnsure XAMPP Apache is running and test in browser first."
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
                Text("Sign Up", color = Color.White, fontSize = 16.sp)
            }
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
