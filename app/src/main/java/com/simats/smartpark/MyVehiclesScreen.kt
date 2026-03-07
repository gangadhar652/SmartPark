package com.simats.smartpark

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.smartpark.api.ApiClient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyVehiclesScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    var vehicleNumber by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val sharedPrefs = context.getSharedPreferences("SmartParkPrefs", android.content.Context.MODE_PRIVATE)
        val email = sharedPrefs.getString("user_email", "") ?: ""
        
        if (email.isNotEmpty()) {
            try {
                val response = ApiClient.apiService.getUserProfile(email)
                if (response.isSuccessful) {
                    vehicleNumber = response.body()?.data?.vehicle_number ?: "No vehicle added"
                }
            } catch (e: Exception) {
                vehicleNumber = "Error loading vehicle"
            } finally {
                isLoading = false
            }
        } else {
            isLoading = false
            vehicleNumber = "Not logged in"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Vehicles", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F1F5))
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.DirectionsCar,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = Color(0xFF2196F3)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("Primary Vehicle", fontSize = 14.sp, color = Color.Gray)
                        if (isLoading) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp))
                        } else {
                            Text(
                                text = vehicleNumber,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}
