package com.example.smartpark

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen(
    onHomeClick: () -> Unit,
    onEvClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Scaffold(
        bottomBar = {
            ProfileBottomNavBar(
                onHomeClick = onHomeClick,
                onEvClick = onEvClick
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

                    Column {
                        Text("Rajesh Kumar", fontWeight = FontWeight.Bold)
                        Text("+91 98765 43210", fontSize = 12.sp, color = Color.Gray)

                        Spacer(modifier = Modifier.height(4.dp))

                        Surface(
                            color = Color(0xFFE3F2FD),
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(
                                "Premium Member",
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
                                fontSize = 11.sp,
                                color = Color(0xFF1976D2)
                            )
                        }
                    }
                }

                Divider()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    ProfileStat("24", "Bookings")
                    ProfileStat("156", "Points")
                    ProfileStat("4.9", "Rating")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            /* 🔹 MENU */
            ProfileMenuItem(Icons.Default.DirectionsCar, "My Vehicles", "2")
            ProfileMenuItem(Icons.Default.AccountBalanceWallet, "Wallet & Payments", "₹250")
            ProfileMenuItem(Icons.Default.Star, "Membership Plans")
            ProfileMenuItem(Icons.Default.Notifications, "Notifications")
            ProfileMenuItem(Icons.Default.Lock, "Privacy & Security")
            ProfileMenuItem(Icons.Default.HelpOutline, "Help & Support")

            Spacer(modifier = Modifier.height(20.dp))

            /* 🔹 LOGOUT */
            TextButton(
                onClick = onLogoutClick,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Icon(Icons.Default.Logout, contentDescription = null, tint = Color.Red)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Logout", color = Color.Red)
            }
        }
    }
}

/* ---------------- STATS ---------------- */

@Composable
fun ProfileStat(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontWeight = FontWeight.Bold)
        Text(label, fontSize = 12.sp, color = Color.Gray)
    }
}

/* ---------------- MENU ITEM ---------------- */

@Composable
fun ProfileMenuItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    badge: String? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { },
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
                    color = Color(0xFFE3F2FD),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        badge,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        fontSize = 11.sp,
                        color = Color(0xFF1976D2)
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
    onEvClick: () -> Unit
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
            onClick = {},
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
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Default.AccountBalanceWallet, null) },
            label = { Text("Wallet") }
        )

        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = { Icon(Icons.Default.Person, null) },
            label = { Text("Profile") }
        )
    }
}
