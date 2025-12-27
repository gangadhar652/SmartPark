package com.example.smartpark

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FindParkingScreen(
    onHomeClick: () -> Unit,
    onChennaiClick: () -> Unit,
    onHyderabadClick: () -> Unit,
    onBangaloreClick: () -> Unit,
    onMumbaiClick: () -> Unit,
    onEvClick: () -> Unit,
    onProfileClick: () -> Unit,
    onBookingsClick: () -> Unit
) {

    var selectedTab by remember { mutableStateOf("Parking") }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selected = "Home",
                onHomeClick = onHomeClick,
                onBookingsClick = onBookingsClick,
                onEvClick = onEvClick,
                onProfileClick = onProfileClick
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = "Find Parking",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Real-time availability near you",
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            /* 🔹 Tabs */
            Row {
                TabButton("Parking", selectedTab) {
                    selectedTab = "Parking"
                }

                Spacer(modifier = Modifier.width(8.dp))

                TabButton("EV Charging", selectedTab) {
                    onEvClick()
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Search location or parking name...") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text("Select City", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            CityGrid(
                onChennaiClick,
                onHyderabadClick,
                onBangaloreClick,
                onMumbaiClick
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text("Nearby Parking", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            ParkingCard(
                "City Mall Parking",
                "Downtown, 0.5 km",
                "₹40/hr • EV Available"
            )

            ParkingCard(
                "Metro Parking",
                "Central Station, 0.8 km",
                "₹30/hr"
            )

            ParkingCard(
                "Plaza Parking",
                "Market Road, 1.2 km",
                "₹50/hr • Covered"
            )
        }
    }
}

/* ---------------- TAB BUTTON ---------------- */

@Composable
fun TabButton(
    text: String,
    selected: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor =
                if (text == selected) Color(0xFFE3F2FD)
                else Color.White
        )
    ) {
        Text(text, color = Color.Black)
    }
}

/* ---------------- CITY GRID ---------------- */

@Composable
fun CityGrid(
    onChennaiClick: () -> Unit,
    onHyderabadClick: () -> Unit,
    onBangaloreClick: () -> Unit,
    onMumbaiClick: () -> Unit
) {

    val cities = listOf(
        Triple("Chennai", "45 spots", Color(0xFFD32F2F)),
        Triple("Hyderabad", "38 spots", Color(0xFF1976D2)),
        Triple("Bangalore", "52 spots", Color(0xFF388E3C)),
        Triple("Mumbai", "67 spots", Color(0xFFF57C00))
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.height(220.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        items(cities) { city ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        when (city.first) {
                            "Chennai" -> onChennaiClick()
                            "Hyderabad" -> onHyderabadClick()
                            "Bangalore" -> onBangaloreClick()
                            "Mumbai" -> onMumbaiClick()
                        }
                    },
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(4.dp, city.third),
                colors = CardDefaults.cardColors(
                    containerColor = city.third.copy(alpha = 0.12f)
                )
            ) {

                Box(modifier = Modifier.padding(16.dp)) {

                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            city.first,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = city.third
                        )
                        Text(
                            "${city.second} available",
                            fontSize = 13.sp,
                            color = Color.DarkGray
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.ArrowForwardIos,
                        contentDescription = null,
                        tint = city.third,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(18.dp)
                    )
                }
            }
        }
    }
}

/* ---------------- PARKING CARD ---------------- */

@Composable
fun ParkingCard(
    name: String,
    location: String,
    price: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(Icons.Default.LocationOn, null)

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(name, fontWeight = FontWeight.Bold)
                Text(location, fontSize = 12.sp)
                Text(price, fontSize = 12.sp)
            }

            Text(
                text = "Available",
                color = Color(0xFF2E7D32),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/* ---------------- BOTTOM NAV BAR ---------------- */

@Composable
fun BottomNavBar(
    selected: String,
    onHomeClick: () -> Unit,
    onBookingsClick: () -> Unit,
    onEvClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    NavigationBar {

        NavigationBarItem(
            selected = selected == "Home",
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
            selected = selected == "EV",
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
            selected = selected == "Profile",
            onClick = onProfileClick,
            icon = { Icon(Icons.Default.Person, null) },
            label = { Text("Profile") }
        )
    }
}
