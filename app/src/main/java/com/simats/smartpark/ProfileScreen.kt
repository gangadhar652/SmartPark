package com.simats.smartpark

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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

@Composable
fun ProfileScreen(
    onHomeClick: () -> Unit,
    onEvClick: () -> Unit,
    onBookingsClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onEditProfileClick: () -> Unit = {},
    onMembershipClick: () -> Unit = {},
    onVehiclesClick: () -> Unit = {},
    onPrivacyClick: () -> Unit = {}, // ✅ ADDED
    refreshKey: Int = 0
) {
    val context = LocalContext.current
    
    var userName by remember { mutableStateOf("Loading...") }
    var userPhone by remember { mutableStateOf("") }
    var userVehicleNumber by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }
    var isPremium by remember { mutableStateOf(false) }
    
    // Fetch user profile - refresh when refreshKey changes or on initial load
    LaunchedEffect(refreshKey) {
        isLoading = true
        val sharedPrefs = context.getSharedPreferences("SmartParkPrefs", android.content.Context.MODE_PRIVATE)
        val subPrefs = context.getSharedPreferences("subscription_prefs", android.content.Context.MODE_PRIVATE)
        
        isPremium = subPrefs.getBoolean("is_premium_user", false)
        val userEmail = sharedPrefs.getString("user_email", null)
        
        if (userEmail != null && userEmail.isNotEmpty()) {
            try {
                val response = ApiClient.apiService.getUserProfile(userEmail)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status == "success" && body.data != null) {
                        userName = body.data.full_name
                        userPhone = body.data.phone
                        userVehicleNumber = body.data.vehicle_number ?: ""
                    } else {
                        userName = "User"
                        userPhone = ""
                    }
                } else {
                    userName = "User"
                    userPhone = ""
                }
            } catch (e: Exception) {
                userName = "User"
                userPhone = ""
            } finally {
                isLoading = false
            }
        } else {
            userName = "Guest User"
            userPhone = ""
            isLoading = false
        }
    }
    Scaffold(
        bottomBar = {
            ProfileBottomNavBar(
                onHomeClick = onHomeClick,
                onEvClick = onEvClick,
                onBookingsClick = onBookingsClick
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            /* 🔹 PROFILE CARD */
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .background(Color(0xFF2196F3), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isLoading) "Loading..." else userName,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (isLoading) "" else userPhone,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        if (!isLoading && userVehicleNumber.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.DirectionsCar,
                                    contentDescription = null,
                                    tint = Color(0xFF2196F3),
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = userVehicleNumber,
                                    fontSize = 12.sp,
                                    color = Color(0xFF2196F3),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    IconButton(
                        onClick = onEditProfileClick,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit Profile",
                            tint = Color(0xFF2196F3)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            /* 🔹 MENU */
            ProfileMenuItem(
                icon = Icons.Default.DirectionsCar,
                title = "My Vehicles",
                badge = if (userVehicleNumber.isNotEmpty()) userVehicleNumber else null,
                onClick = onVehiclesClick
            )
            
            ProfileMenuItem(
                icon = Icons.Default.Star, 
                title = "Membership Plans",
                badge = if (isPremium) "₹100 / Month" else "Free Plan",
                badgeColor = if (isPremium) Color(0xFFFFD700) else Color(0xFFE3F2FD),
                badgeTextColor = if (isPremium) Color.Black else Color(0xFF1976D2),
                onClick = onMembershipClick 
            )
            
            ProfileMenuItem(
                icon = Icons.Default.Lock,
                title = "Privacy & Security",
                onClick = onPrivacyClick // ✅ ASSIGNED CALLBACK
            )

            Spacer(modifier = Modifier.height(20.dp))

            /* 🔹 LOGOUT */
            TextButton(
                onClick = onLogoutClick,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Icon(Icons.Default.Logout, contentDescription = null, tint = Color.Red)
                Spacer(modifier = Modifier.width(6.6.dp))
                Text("Logout", color = Color.Red)
            }
        }
    }
}

/* ---------------- MENU ITEM ---------------- */

@Composable
fun ProfileMenuItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    badge: String? = null,
    badgeColor: Color = Color(0xFFE3F2FD),
    badgeTextColor: Color = Color(0xFF1976D2),
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = Color.Gray)

            Spacer(modifier = Modifier.width(12.dp))

            Text(title, modifier = Modifier.weight(1f))

            if (badge != null) {
                Surface(
                    color = badgeColor,
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        badge,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
                        fontSize = 11.sp,
                        color = badgeTextColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.width(6.dp))
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
        }
    }
}

/* ---------------- BOTTOM NAV ---------------- */

@Composable
fun ProfileBottomNavBar(
    onHomeClick: () -> Unit,
    onEvClick: () -> Unit,
    onBookingsClick: () -> Unit
) {
    NavigationBar {

        NavigationBarItem(
            selected = false,
            onClick = onHomeClick,
            icon = { Icon(Icons.Default.Home, null) },
            label = { Text("Home") }
        )

        NavigationBarItem(
            selected = false,
            onClick = onBookingsClick,
            icon = { Icon(Icons.Default.Event, null) },
            label = { Text("Bookings") }
        )

        NavigationBarItem(
            selected = false,
            onClick = onEvClick,
            icon = { Icon(Icons.Default.ElectricCar, null) },
            label = { Text("EV") }
        )

        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = { Icon(Icons.Default.Person, null) },
            label = { Text("Profile") }
        )
    }
}
