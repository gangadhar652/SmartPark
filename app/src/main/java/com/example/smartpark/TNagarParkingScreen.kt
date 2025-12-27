package com.example.smartpark

import androidx.compose.foundation.layout.*
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TNagarParkingScreen(
    onBackClick: () -> Unit = {},
    onNavigateClick: () -> Unit = {},
    onBookNowClick: () -> Unit = {}
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            BottomBar(onNavigateClick, onBookNowClick)
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("T Nagar Parking", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text("T Nagar, Chennai", fontSize = 12.sp, color = Color.Gray)
                        }

                        Surface(
                            color = Color(0xFFDFF5EA),
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(
                                "Available",
                                color = Color(0xFF2E7D32),
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, null, tint = Color(0xFFFFC107), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("4.6", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("2,180 reviews", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            /* ✅ weight applied in Row, NOT inside Card */
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                InfoCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.LocalParking,
                    title = "Slots",
                    value = "58 / 140"
                )
                InfoCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.ElectricCar,
                    title = "EV Charging",
                    value = "10 Available"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                InfoCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Schedule,
                    title = "Price / Hour",
                    value = "₹50"
                )
                InfoCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Security,
                    title = "Security",
                    value = "CCTV 24/7"
                )
            }
        }
    }
}

/* ---------- INFO CARD (FIXED) ---------- */

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    value: String
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Icon(icon, null, tint = Color(0xFF1976D2))
            Spacer(modifier = Modifier.height(6.dp))
            Text(title, fontSize = 12.sp, color = Color.Gray)
            Text(value, fontWeight = FontWeight.Bold)
        }
    }
}

/* ---------- BOTTOM BAR ---------- */

@Composable
fun BottomBar(
    onNavigateClick: () -> Unit,
    onBookNowClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        OutlinedButton(
            onClick = onNavigateClick,
            modifier = Modifier.weight(1f)
        ) {
            Icon(Icons.Default.Navigation, null)
            Spacer(modifier = Modifier.width(6.dp))
            Text("Navigate")
        }

        Button(
            onClick = onBookNowClick,
            modifier = Modifier.weight(1f)
        ) {
            Text("Book Now")
        }
    }
}
