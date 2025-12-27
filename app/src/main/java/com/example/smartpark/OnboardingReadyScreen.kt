package com.example.smartpark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OnboardingReadyScreen(
    onGetStartedClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(90.dp))

        // 🔵 Illustration placeholder
        Box(
            modifier = Modifier
                .size(160.dp)
                .background(Color(0xFFE3F2FD), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "🚀",
                fontSize = 36.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Ready to Start?",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Join thousands of smart parkers and experience hassle-free parking today.",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onGetStartedClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3)
            )
        ) {
            Text("Get Started", color = Color.White)
        }
    }
}
