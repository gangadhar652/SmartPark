package com.example.smartpark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalParking
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OnboardingScreen(
    onNextClick: () -> Unit,
    onSkipClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(80.dp))

        /* 🔵 PARKING ICON CIRCLE */
        Box(
            modifier = Modifier
                .size(160.dp)
                .background(Color(0xFFE3F2FD), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.LocalParking,
                contentDescription = "Parking Icon",
                tint = Color(0xFF2196F3),
                modifier = Modifier.size(72.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        /* 🔤 TITLE */
        Text(
            text = "Real-Time Parking",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        /* 📝 DESCRIPTION */
        Text(
            text = "Locate parking spots instantly with live availability across the city.",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(48.dp))

        /* ▶ NEXT BUTTON */
        Button(
            onClick = onNextClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3)
            )
        ) {
            Text("Next", color = Color.White)
        }

        Spacer(modifier = Modifier.height(12.dp))

        /* ⏭ SKIP BUTTON */
        TextButton(onClick = onSkipClick) {
            Text("Skip", color = Color.Gray)
        }
    }
}
