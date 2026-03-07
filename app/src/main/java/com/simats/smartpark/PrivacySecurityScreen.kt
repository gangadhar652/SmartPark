package com.simats.smartpark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacySecurityScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Privacy & Security", fontWeight = FontWeight.Bold) },
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
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Text(
                text = "Privacy & Security Policy",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Smart Park is committed to protecting the privacy and security of its users. We understand the importance of safeguarding personal information and ensure that all data is handled responsibly and transparently. The application collects only the minimum information required to provide its services effectively.",
                fontSize = 14.sp,
                color = Color.DarkGray,
                lineHeight = 20.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Personal details such as name, email address, and phone number are collected during registration to manage user accounts and provide personalized services. Location data is used only to display nearby parking locations and enable navigation features. Parking and EV charging booking information is stored to help users view their booking history and manage reservations.",
                fontSize = 14.sp,
                color = Color.DarkGray,
                lineHeight = 20.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Smart Park does not collect unnecessary or sensitive personal data. All information collected is used strictly for application functionality, service improvement, and user support. The app does not sell, rent, or trade user data to third parties under any circumstances.",
                fontSize = 14.sp,
                color = Color.DarkGray,
                lineHeight = 20.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "We implement appropriate security measures to protect user data from unauthorized access, loss, misuse, or alteration. Secure storage practices and controlled access mechanisms are used to maintain data integrity and confidentiality.",
                fontSize = 14.sp,
                color = Color.DarkGray,
                lineHeight = 20.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Users have full control over their data. They can update their profile information, manage notification preferences, and request account deletion at any time. Upon account deletion, user data is removed in accordance with applicable policies.",
                fontSize = 14.sp,
                color = Color.DarkGray,
                lineHeight = 20.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Smart Park may use basic, non-identifiable analytics to improve app performance and user experience. These analytics do not track users personally and are used only to enhance app reliability and usability.",
                fontSize = 14.sp,
                color = Color.DarkGray,
                lineHeight = 20.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "The application is not intended for children under the age of 13, and we do not knowingly collect data from minors. If such data is identified, it will be removed promptly.",
                fontSize = 14.sp,
                color = Color.DarkGray,
                lineHeight = 20.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "We may update this Privacy & Security policy as the application evolves. Any changes will be reflected within the app to keep users informed.",
                fontSize = 14.sp,
                color = Color.DarkGray,
                lineHeight = 20.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "By using Smart Park, users agree to this Privacy & Security policy. We are committed to maintaining a safe, secure, and trustworthy platform for smart parking and EV charging services.",
                fontSize = 14.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 20.sp
            )
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
