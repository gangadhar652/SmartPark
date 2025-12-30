package com.example.smartpark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VerifyOtpScreen(
    onBackClick: () -> Unit,
    onVerifyClick: () -> Unit
) {
    var otp by remember { mutableStateOf("") }

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
            text = "Enter the 4-digit code sent to your email",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = otp,
            onValueChange = { if (it.length <= 4) otp = it },
            placeholder = { Text("0000", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center, fontSize = 24.sp, letterSpacing = 8.sp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onVerifyClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3)
            )
        ) {
            Text("Verify", color = Color.White, fontSize = 16.sp)
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
