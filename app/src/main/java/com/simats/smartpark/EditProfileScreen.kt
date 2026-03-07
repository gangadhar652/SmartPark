package com.simats.smartpark

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.smartpark.api.ApiClient
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    onBackClick: () -> Unit,
    initialName: String = "",
    initialEmail: String = "",
    initialPhone: String = ""
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    
    var fullName by remember { mutableStateOf(initialName) }
    var email by remember { mutableStateOf(initialEmail) }
    var phone by remember { mutableStateOf(initialPhone) }
    var vehicleNumber by remember { mutableStateOf("") }
    var isLoadingData by remember { mutableStateOf(true) }
    
    // Load user data on screen load
    LaunchedEffect(Unit) {
        val sharedPrefs = context.getSharedPreferences("SmartParkPrefs", android.content.Context.MODE_PRIVATE)
        val userEmail = sharedPrefs.getString("user_email", null)
        
        if (userEmail != null && initialEmail.isEmpty()) {
            scope.launch {
                try {
                    val response = ApiClient.apiService.getUserProfile(userEmail)
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null && body.status == "success" && body.data != null) {
                            fullName = body.data.full_name
                            email = body.data.email
                            phone = body.data.phone
                            vehicleNumber = body.data.vehicle_number ?: ""
                        }
                    }
                } catch (e: Exception) {
                    // Keep default values on error
                } finally {
                    isLoadingData = false
                }
            }
        } else {
            if (initialEmail.isNotEmpty()) {
                email = initialEmail
                fullName = initialName
                phone = initialPhone
                vehicleNumber = ""
            }
            isLoadingData = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Profile") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            /* 👤 FULL NAME */
            Text(
                text = "Full Name",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                placeholder = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                enabled = false
            )

            Spacer(modifier = Modifier.height(20.dp))

            /* 📧 EMAIL */
            Text(
                text = "Email Address",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Email Address") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                enabled = false,
                readOnly = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            /* 📞 PHONE */
            Text(
                text = "Phone Number",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                placeholder = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                enabled = false
            )

            Spacer(modifier = Modifier.height(20.dp))

            /* 🚗 VEHICLE NUMBER */
            Text(
                text = "Vehicle Number",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = vehicleNumber,
                onValueChange = { vehicleNumber = it },
                placeholder = { Text("Vehicle Number") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                enabled = false
            )
        }
    }
}
