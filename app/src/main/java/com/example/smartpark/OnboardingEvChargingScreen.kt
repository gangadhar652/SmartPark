package com.example.smartpark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OnboardingEvChargingScreen(
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

        Spacer(modifier = Modifier.height(100.dp))

        // Icon placeholder (simple circle look without image)
        Box(
            modifier = Modifier
                .size(140.dp)
                .background(Color(0xFFDFF7EA)),
            contentAlignment = Alignment.Center
        ) {
            Text("⚡", fontSize = 32.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "EV Charging Support",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Find nearby EV charging slots with live availability and fast charging options.",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onNextClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Next")
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(onClick = onSkipClick) {
            Text("Skip", color = Color.Gray)
        }
    }
}
