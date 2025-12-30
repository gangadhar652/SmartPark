package com.example.smartpark

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyBookingsScreen(
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onEvClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("All", "Parking", "EV Charging")

    Scaffold(
        bottomBar = {
            MyBookingsBottomNavBar(
                onHomeClick = onHomeClick,
                onEvClick = onEvClick,
                onProfileClick = onProfileClick
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF6FAFF))
                .padding(padding)
                .padding(16.dp)
        ) {

            /* HEADER */
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Column {
                    Text(
                        text = "My Bookings",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "View your booking history",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            /* TABS */
            Row {
                tabs.forEachIndexed { index, title ->
                    FilterTab(
                        title = title,
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            /* BOOKINGS LIST */
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                item {
                    BookingCard(
                        title = "City Mall Parking",
                        location = "MG Road, Bangalore",
                        date = "Today",
                        duration = "2 hours",
                        slot = "Slot A3",
                        price = "₹104",
                        status = "Active",
                        isEv = false
                    )
                }

                item {
                    BookingCard(
                        title = "Tech Park Charging Hub",
                        location = "Whitefield, Bangalore",
                        date = "Yesterday",
                        duration = "1 hour",
                        slot = "Type 2 Connector",
                        price = "₹586",
                        status = "Completed",
                        isEv = true
                    )
                }

                item {
                    BookingCard(
                        title = "Kempegowda Airport",
                        location = "Bangalore",
                        date = "Dec 18, 2024",
                        duration = "4 hours",
                        slot = "Slot B12",
                        price = "₹320",
                        status = "Completed",
                        isEv = false
                    )
                }
            }
        }
    }
}

/* ---------- TAB ---------- */

@Composable
fun FilterTab(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = if (selected) Color(0xFFE3F2FD) else Color.White,
        border = if (!selected) BorderStroke(1.dp, Color(0xFFE0E0E0)) else null
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            fontSize = 13.sp,
            color = if (selected) Color(0xFF1976D2) else Color.Gray
        )
    }
}

/* ---------- BOOKING CARD ---------- */

@Composable
fun BookingCard(
    title: String,
    location: String,
    date: String,
    duration: String,
    slot: String,
    price: String,
    status: String,
    isEv: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(
                    imageVector = if (isEv) Icons.Default.ElectricCar else Icons.Default.LocalParking,
                    contentDescription = null,
                    tint = if (isEv) Color(0xFF2E7D32) else Color(0xFF1565C0)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                StatusChip(status)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(location, fontSize = 12.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BookingInfo(Icons.Default.CalendarToday, date)
                BookingInfo(Icons.Default.Schedule, duration)
            }

            Spacer(modifier = Modifier.height(6.dp))

            BookingInfo(Icons.Default.ConfirmationNumber, slot)

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = price,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

/* ---------- STATUS CHIP ---------- */

@Composable
fun StatusChip(status: String) {
    val background = if (status == "Active") Color(0xFFE8F5E9) else Color(0xFFE0E0E0)
    val textColor = if (status == "Active") Color(0xFF2E7D32) else Color.DarkGray

    Surface(
        shape = RoundedCornerShape(50),
        color = background
    ) {
        Text(
            text = status,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            fontSize = 11.sp,
            color = textColor
        )
    }
}

/* ---------- INFO ROW ---------- */

@Composable
fun BookingInfo(
    icon: ImageVector,
    text: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = Color.Gray, modifier = Modifier.size(14.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text, fontSize = 12.sp, color = Color.Gray)
    }
}

/* ---------- BOTTOM NAV ---------- */

@Composable
fun MyBookingsBottomNavBar(
    onHomeClick: () -> Unit,
    onEvClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    NavigationBar {

        NavigationBarItem(
            selected = false,
            onClick = onHomeClick,
            icon = { Icon(Icons.Default.Home, null) },
            label = { Text("Home") }
        )

        NavigationBarItem(
            selected = true,
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
            selected = false,
            onClick = onProfileClick,
            icon = { Icon(Icons.Default.Person, null) },
            label = { Text("Profile") }
        )
    }
}
