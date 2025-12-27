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
import java.text.SimpleDateFormat
import java.util.*

/* ---------- DATA MODELS ---------- */

data class ParkingArea(
    val name: String,
    val slots: String,
    val evSlots: String,
    val price: String
)

data class DayItem(val day: String, val date: String)
data class TimeSlot(val title: String, val time: String)

/* ---------- DATE HELPER ---------- */

fun getNext7DaysList(): List<DayItem> {
    val list = mutableListOf<DayItem>()
    val cal = Calendar.getInstance()
    val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())
    val dateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())

    repeat(7) {
        list.add(DayItem(dayFormat.format(cal.time), dateFormat.format(cal.time)))
        cal.add(Calendar.DAY_OF_YEAR, 1)
    }
    return list
}

/* ---------- TIME SLOTS ---------- */

val timeSlots = listOf(
    TimeSlot("Morning", "6 AM - 12 PM (6 hrs)"),
    TimeSlot("Afternoon", "12 PM - 6 PM (6 hrs)"),
    TimeSlot("Night", "6 PM - 12 AM (6 hrs)")
)

/* ---------- MAIN SCREEN ---------- */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkingDetailsScreen(
    area: ParkingArea,
    onBackClick: () -> Unit,
    onBookNowClick: () -> Unit
) {

    val days = remember { getNext7DaysList() }
    var selectedDay by remember { mutableStateOf(days.first()) }
    var selectedSlot by remember { mutableStateOf(timeSlots.first()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Parking Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                }
            )
        },
        bottomBar = {
            ParkingBottomBar {
                onBookNowClick()
            }
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
                Column(Modifier.padding(16.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("${area.name} Parking", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text("${area.name}, Chennai", fontSize = 12.sp, color = Color.Gray)
                        }
                        ParkingStatusChip("Available")
                    }

                    Spacer(Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, null, tint = Color(0xFFFFC107), modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("4.5", fontWeight = FontWeight.Bold)
                        Spacer(Modifier.width(6.dp))
                        Text("1,200+ reviews", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            /* ---------- DAY ---------- */
            Text("Select Day", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(days.size) {
                    val day = days[it]
                    DayCard(day, day == selectedDay) { selectedDay = day }
                }
            }

            Spacer(Modifier.height(16.dp))

            /* ---------- TIME ---------- */
            Text("Select Time Slot", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(timeSlots.size) {
                    val slot = timeSlots[it]
                    TimeSlotCard(slot, slot == selectedSlot) { selectedSlot = slot }
                }
            }

            Spacer(Modifier.height(16.dp))

            /* ---------- INFO ---------- */
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ParkingInfoCard(Modifier.weight(1f), Icons.Default.LocalParking, "Slots", area.slots)
                ParkingInfoCard(Modifier.weight(1f), Icons.Default.ElectricCar, "EV Charging", area.evSlots)
            }

            Spacer(Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ParkingInfoCard(Modifier.weight(1f), Icons.Default.Schedule, "Price / Hour", area.price)
                ParkingInfoCard(Modifier.weight(1f), Icons.Default.Security, "Security", "CCTV 24/7")
            }
        }
    }
}

/* ---------- UI HELPERS ---------- */

@Composable
fun DayCard(day: DayItem, selected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier.width(72.dp).clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (selected) Color(0xFF1976D2) else Color(0xFFE3F2FD)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(day.day, fontWeight = FontWeight.Bold, color = if (selected) Color.White else Color.Black)
            Text(day.date, fontSize = 12.sp, color = if (selected) Color.White else Color.DarkGray)
        }
    }
}

@Composable
fun TimeSlotCard(slot: TimeSlot, selected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier.width(150.dp).clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (selected) Color(0xFF2E7D32) else Color(0xFFE8F5E9)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(slot.title, fontWeight = FontWeight.Bold,
                color = if (selected) Color.White else Color.Black)
            Text(slot.time, fontSize = 12.sp,
                color = if (selected) Color.White else Color.DarkGray)
        }
    }
}

@Composable
fun ParkingInfoCard(
    modifier: Modifier,
    icon: ImageVector,
    title: String,
    value: String
) {
    Card(modifier, RoundedCornerShape(12.dp)) {
        Column(Modifier.padding(12.dp)) {
            Icon(icon, null, tint = Color(0xFF1976D2))
            Spacer(Modifier.height(6.dp))
            Text(title, fontSize = 12.sp, color = Color.Gray)
            Text(value, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ParkingStatusChip(text: String) {
    Surface(shape = RoundedCornerShape(50), color = Color(0xFFDFF5EA)) {
        Text(text, fontSize = 12.sp, color = Color(0xFF2E7D32),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp))
    }
}

@Composable
fun ParkingBottomBar(onBookNowClick: () -> Unit) {
    Button(
        onClick = onBookNowClick,
        modifier = Modifier.fillMaxWidth().padding(12.dp)
    ) {
        Text("Book Now")
    }
}

/* ---------- SAMPLE DATA ---------- */

val T_NAGAR = ParkingArea("T Nagar", "58 / 140", "10 Available", "₹50")
val ANNA_NAGAR = ParkingArea("Anna Nagar", "42 / 120", "6 Available", "₹40")
val VELACHERY = ParkingArea("Velachery", "65 / 150", "12 Available", "₹60")
val ADYAR = ParkingArea("Adyar", "30 / 90", "4 Available", "₹45")
val MYLAPORE = ParkingArea("Mylapore", "28 / 80", "3 Available", "₹35")
