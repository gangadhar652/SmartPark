package com.example.smartpark

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

@Composable
fun FindEvChargingScreen(
    onHomeClick: () -> Unit,
    onProfileClick: () -> Unit,
    onMumbaiEvClick: () -> Unit,
    onDelhiEvClick: () -> Unit,
    onBangaloreEvClick: () -> Unit,
    onPuneEvClick: () -> Unit          // ✅ ADDED
) {

    val timeSession = getTimeSession()

    Scaffold(
        bottomBar = {
            EvBottomNavBar(
                onHomeClick = onHomeClick,
                onProfileClick = onProfileClick
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            Text(
                text = "Find EV Charging Stations",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Today", fontSize = 12.sp, color = Color.Gray)

                Spacer(modifier = Modifier.width(8.dp))

                Surface(
                    color = Color(0xFFDFF5E1),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        timeSession,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        fontSize = 11.sp,
                        color = Color(0xFF2E7D32)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("Select City", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            EvCityGrid(
                onMumbaiClick = onMumbaiEvClick,
                onDelhiClick = onDelhiEvClick,
                onBangaloreClick = onBangaloreEvClick,
                onPuneClick = onPuneEvClick          // ✅ PASS
            )

            Spacer(modifier = Modifier.height(24.dp))

            ChargingTipsCard()
        }
    }
}

/* ---------- TIME SESSION ---------- */

fun getTimeSession(): String {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return when (hour) {
        in 5..11 -> "Morning"
        in 12..16 -> "Afternoon"
        in 17..20 -> "Evening"
        else -> "Night"
    }
}

/* ---------- CITY GRID ---------- */

@Composable
fun EvCityGrid(
    onMumbaiClick: () -> Unit,
    onDelhiClick: () -> Unit,
    onBangaloreClick: () -> Unit,
    onPuneClick: () -> Unit           // ✅ ADDED
) {
    val cities = listOf(
        Triple("Mumbai", "67 stations", Color(0xFF4DB6AC)),
        Triple("Delhi", "52 stations", Color(0xFF64B5F6)),
        Triple("Bangalore", "48 stations", Color(0xFF81C784)),
        Triple("Pune", "32 stations", Color(0xFF66BB6A))
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.height(280.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(cities) { city ->
            EvCityCard(
                name = city.first,
                count = city.second,
                color = city.third
            ) {
                when (city.first) {
                    "Mumbai" -> onMumbaiClick()
                    "Delhi" -> onDelhiClick()
                    "Bangalore" -> onBangaloreClick()
                    "Pune" -> onPuneClick()          // ✅ ADDED
                }
            }
        }
    }
}

/* ---------- CITY CARD ---------- */

@Composable
fun EvCityCard(
    name: String,
    count: String,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(color, color.copy(alpha = 0.85f))
                    )
                )
                .padding(12.dp)
        ) {

            Icon(
                Icons.Default.ElectricCar,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.align(Alignment.TopStart)
            )

            Column(modifier = Modifier.align(Alignment.BottomStart)) {
                Text(name, color = Color.White, fontWeight = FontWeight.Bold)
                Text(count, color = Color.White, fontSize = 12.sp)
            }

            Icon(
                Icons.Default.ArrowForward,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
    }
}

/* ---------- CHARGING TIPS ---------- */

@Composable
fun ChargingTipsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text("Charging Tips", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Charge during off-peak hours (10 PM – 6 AM) to save up to 30% on charging costs.",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(onClick = {}) {
                Text("View All Tips")
            }
        }
    }
}

/* ---------- BOTTOM NAV ---------- */

@Composable
fun EvBottomNavBar(
    onHomeClick: () -> Unit,
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
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Default.Event, null) },
            label = { Text("Bookings") }
        )

        NavigationBarItem(
            selected = true,
            onClick = {},
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
