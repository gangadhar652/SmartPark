package com.example.smartpark

import androidx.compose.foundation.BorderStroke
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
fun HyderabadBookingConfirmationScreen(
    onBackHomeClick: () -> Unit,
    onViewBookingsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        /* ---------- SUCCESS ICON ---------- */
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color(0xFFE8F5E9), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Check,
                contentDescription = null,
                tint = Color(0xFF2E7D32),
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Booking Confirmed!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Your parking slot is reserved",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        /* ---------- QR PLACEHOLDER ---------- */
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FB))
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(Color(0xFFEDF1F5), RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.QrCodeScanner,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(64.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Scan this QR code at the parking entrance",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        /* ---------- BOOKING DETAILS ---------- */
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F1F5))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                HyderabadBookingDetailRow(
                    icon = Icons.Default.Schedule,
                    title = "Duration",
                    value = "6 hours"
                )

                Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color.LightGray)

                HyderabadBookingDetailRow(
                    icon = Icons.Default.DirectionsCar,
                    title = "Vehicle",
                    value = "TS 09 AB 1234"
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        /* ---------- ACTIONS ---------- */
        Button(
            onClick = onViewBookingsClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6750A4)) 
        ) {
            Text("View My Bookings", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onBackHomeClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Text("Back to Home", fontSize = 16.sp, color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun HyderabadBookingDetailRow(
    icon: ImageVector,
    title: String,
    value: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {

        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFFE3F2FD), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = Color(0xFF1976D2),
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(title, fontSize = 12.sp, color = Color.Gray)
            Text(value, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}
