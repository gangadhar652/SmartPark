package com.example.smartpark

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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

/* ---------- DATA MODEL ---------- */

data class BangaloreParkingArea(
    val name: String,
    val slots: String,
    val evSlots: String,
    val price: String
)

/* ---------- MAIN SCREEN ---------- */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BangaloreParkingDetailsScreen(
    area: BangaloreParkingArea,
    onBackClick: () -> Unit,
    onBookNowClick: () -> Unit
) {

    val days = remember { getNext7DaysList() }
    var selectedDay by remember { mutableStateOf(days.first()) }
    var selectedSlot by remember { mutableStateOf(timeSlots.first()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bangalore Parking Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            BangaloreParkingBottomBar(onBookNowClick)
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            /* ---------- TITLE CARD ---------- */
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
                            Text(
                                text = "${area.name} Parking",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${area.name}, Bangalore",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }

                        BangaloreParkingStatusChip("Available")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFC107),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("4.5", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("850+ reviews", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            /* ---------- DAY ---------- */
            Text("Select Day", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(days.size) {
                    val day = days[it]
                    DayCard(day, day == selectedDay) { selectedDay = day }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            /* ---------- TIME ---------- */
            Text("Select Time Slot", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(timeSlots.size) {
                    val slot = timeSlots[it]
                    TimeSlotCard(slot, slot == selectedSlot) { selectedSlot = slot }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            /* ---------- INFO ROW 1 ---------- */
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                BangaloreInfoCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.LocalParking,
                    title = "Slots",
                    value = area.slots
                )
                BangaloreInfoCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.ElectricCar,
                    title = "EV Charging",
                    value = area.evSlots
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            /* ---------- INFO ROW 2 ---------- */
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                BangaloreInfoCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Schedule,
                    title = "Price / Hour",
                    value = area.price
                )
                BangaloreInfoCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Security,
                    title = "Security",
                    value = "CCTV 24/7"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Amenities", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                BangaloreChip("Covered")
                BangaloreChip("CCTV")
                BangaloreChip("Restroom")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Open Hours", fontWeight = FontWeight.Bold)
            Text("24 / 7 Open", color = Color.Gray)
        }
    }
}

/* ---------- INFO CARD ---------- */

@Composable
fun BangaloreInfoCard(
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
            Icon(icon, contentDescription = null, tint = Color(0xFF1976D2))
            Spacer(modifier = Modifier.height(6.dp))
            Text(title, fontSize = 12.sp, color = Color.Gray)
            Text(value, fontWeight = FontWeight.Bold)
        }
    }
}

/* ---------- CHIP ---------- */

@Composable
fun BangaloreChip(text: String) {
    Surface(
        shape = RoundedCornerShape(50),
        color = Color(0xFFE3F2FD)
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            color = Color(0xFF1976D2),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

/* ---------- STATUS CHIP ---------- */

@Composable
fun BangaloreParkingStatusChip(text: String) {
    Surface(
        color = Color(0xFFDFF5EA),
        shape = RoundedCornerShape(50)
    ) {
        Text(
            text = text,
            color = Color(0xFF2E7D32),
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
        )
    }
}

/* ---------- BOTTOM BAR ---------- */

@Composable
fun BangaloreParkingBottomBar(onBookNowClick: () -> Unit) {
    Button(
        onClick = onBookNowClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text("Book Now")
    }
}

/* ---------- BANGALORE PARKING DATA ---------- */

val KORAMANGALA = BangaloreParkingArea("Koramangala", "65 / 150", "14 Available", "₹60")
val INDIRANAGAR = BangaloreParkingArea("Indiranagar", "50 / 120", "9 Available", "₹55")
val WHITEFIELD = BangaloreParkingArea("Whitefield", "75 / 180", "16 Available", "₹70")
val ELECTRONIC_CITY = BangaloreParkingArea("Electronic City", "60 / 140", "11 Available", "₹65")
val JAYANAGAR = BangaloreParkingArea("Jayanagar", "40 / 100", "6 Available", "₹50")
