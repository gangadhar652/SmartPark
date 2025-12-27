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

data class HyderabadParkingArea(
    val name: String,
    val slots: String,
    val evSlots: String,
    val price: String
)

/* ---------- MAIN SCREEN ---------- */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HyderabadParkingDetailsScreen(
    area: HyderabadParkingArea,
    onBackClick: () -> Unit,
    onBookNowClick: () -> Unit
) {

    val days = remember { getNext7DaysList() }
    var selectedDay by remember { mutableStateOf(days.first()) }
    var selectedSlot by remember { mutableStateOf(timeSlots.first()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hyderabad Parking Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            HyderabadParkingBottomBar(onBookNowClick)
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
                                text = "${area.name}, Hyderabad",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }

                        HyderabadParkingStatusChip("Available")
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
                        Text("4.6", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("900+ reviews", fontSize = 12.sp, color = Color.Gray)
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
                HyderabadInfoCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.LocalParking,
                    title = "Slots",
                    value = area.slots
                )
                HyderabadInfoCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.ElectricCar,
                    title = "EV Charging",
                    value = area.evSlots
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            /* ---------- INFO ROW 2 ---------- */
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                HyderabadInfoCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Schedule,
                    title = "Price / Hour",
                    value = area.price
                )
                HyderabadInfoCard(
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
                HyderabadChip("Covered")
                HyderabadChip("CCTV")
                HyderabadChip("Restroom")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Open Hours", fontWeight = FontWeight.Bold)
            Text("24 / 7 Open", color = Color.Gray)
        }
    }
}

/* ---------- INFO CARD ---------- */

@Composable
fun HyderabadInfoCard(
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
fun HyderabadChip(text: String) {
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
fun HyderabadParkingStatusChip(text: String) {
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
fun HyderabadParkingBottomBar(onBookNowClick: () -> Unit) {
    Button(
        onClick = onBookNowClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text("Book Now")
    }
}

/* ---------- HYDERABAD PARKING DATA ---------- */

val HITECH_CITY = HyderabadParkingArea("Hitech City", "70 / 160", "15 Available", "₹60")
val BANJARA_HILLS = HyderabadParkingArea("Banjara Hills", "45 / 110", "8 Available", "₹55")
val JUBILEE_HILLS = HyderabadParkingArea("Jubilee Hills", "50 / 130", "10 Available", "₹65")
val GACHIBOWLI = HyderabadParkingArea("Gachibowli", "80 / 180", "18 Available", "₹70")
val MADHAPUR = HyderabadParkingArea("Madhapur", "60 / 150", "12 Available", "₹60")
