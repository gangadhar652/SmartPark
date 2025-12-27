package com.example.smartpark

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EnablePermissionsScreen(
    onContinueClick: () -> Unit,
    onSkipClick: () -> Unit
) {
    // ✅ Permission states
    var locationEnabled by remember { mutableStateOf(false) }
    var notificationEnabled by remember { mutableStateOf(false) }
    var cameraEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Enable Permissions",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Allow access for the best experience",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 📍 Location
        PermissionItem(
            title = "Location Access",
            description = "Find nearby parking spots and navigate easily",
            enabled = locationEnabled,
            onClick = { locationEnabled = !locationEnabled }
        )

        // 🔔 Notifications
        PermissionItem(
            title = "Notifications",
            description = "Get alerts for bookings and charging updates",
            enabled = notificationEnabled,
            onClick = { notificationEnabled = !notificationEnabled }
        )

        // 📷 Camera
        PermissionItem(
            title = "Camera Access",
            description = "Scan QR codes for quick parking entry",
            enabled = cameraEnabled,
            onClick = { cameraEnabled = !cameraEnabled }
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onContinueClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Continue", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(
            onClick = onSkipClick,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Skip for now", color = Color.Gray)
        }
    }
}

@Composable
private fun PermissionItem(
    title: String,
    description: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (enabled) Color(0xFFE3F2FD) else Color(0xFFF7F9FC)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = description, fontSize = 13.sp, color = Color.Gray)
            }

            Text(
                text = if (enabled) "Enabled" else "Allow",
                color = if (enabled) Color(0xFF2196F3) else Color.Gray,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
