package com.example.smartpark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChennaiBookingConfirmationScreen(
    onBackHomeClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        /* ---------- SUCCESS ICON ---------- */
        Box(
            modifier = Modifier
                .size(90.dp)
                .background(Color(0xFFDFF5EA), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Check,
                contentDescription = null,
                tint = Color(0xFF2E7D32),
                modifier = Modifier.size(42.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Booking Confirmed!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Your parking slot is reserved",
            fontSize = 13.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        /* ---------- QR PLACEHOLDER ---------- */
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF4F7FB))
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .background(Color(0xFFE9EEF5), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.QrCodeScanner,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(64.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Scan this QR code at the parking entrance",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        /* ---------- BOOKING DETAILS ---------- */
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                BookingDetailRow(
                    icon = Icons.Default.Schedule,
                    title = "Duration",
                    value = "6 hours"
                )

                Divider(modifier = Modifier.padding(vertical = 10.dp))

                BookingDetailRow(
                    icon = Icons.Default.DirectionsCar,
                    title = "Vehicle",
                    value = "TN 01 AB 1234"
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        /* ---------- BACK TO HOME ---------- */
        OutlinedButton(
            onClick = onBackHomeClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Back to Home")
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

/* ---------- DETAIL ROW ---------- */

@Composable
fun BookingDetailRow(
    icon: ImageVector,
    title: String,
    value: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {

        Box(
            modifier = Modifier
                .size(36.dp)
                .background(Color(0xFFE3F2FD), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = Color(0xFF1976D2),
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(title, fontSize = 12.sp, color = Color.Gray)
            Text(value, fontWeight = FontWeight.Bold)
        }
    }
}
