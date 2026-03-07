package com.simats.smartpark

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

data class HyderabadParkingArea(
    val name: String,
    val slots: String,
    val evSlots: String,
    val price: String
)

data class HyderabadDayItem(val day: String, val date: String, val isToday: Boolean = false)
data class HyderabadTimeSlot(val title: String, val time: String, val startHour: Int)

/* ---------- DATE HELPER ---------- */

fun getHyderabadNext7DaysList(): List<HyderabadDayItem> {
    val list = mutableListOf<HyderabadDayItem>()
    val cal = Calendar.getInstance()
    val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())
    val dateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())

    repeat(7) { index ->
        list.add(
            HyderabadDayItem(
                dayFormat.format(cal.time),
                dateFormat.format(cal.time),
                isToday = index == 0
            )
        )
        cal.add(Calendar.DAY_OF_YEAR, 1)
    }
    return list
}

/* ---------- TIME SLOTS ---------- */

val hyderabadTimeSlots = listOf(
    HyderabadTimeSlot("Morning", "6 AM - 12 PM (6 hrs)", 6),
    HyderabadTimeSlot("Afternoon", "12 PM - 6 PM (6 hrs)", 12),
    HyderabadTimeSlot("Night", "6 PM - 12 AM (6 hrs)", 18)
)

fun getAvailableTimeSlots(isToday: Boolean): List<HyderabadTimeSlot> {
    if (!isToday) return hyderabadTimeSlots
    
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return hyderabadTimeSlots.filter { it.startHour + 6 > currentHour }
}

/* ---------- MAIN SCREEN ---------- */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HyderabadParkingDetailsScreen(
    area: HyderabadParkingArea,
    onBackClick: () -> Unit,
    onBookNowClick: (String, String, String, String, String) -> Unit
) {

    val days = remember { getHyderabadNext7DaysList() }
    var selectedDay by remember { mutableStateOf(days.first()) }
    
    val availableTimeSlots = remember(selectedDay) {
        getAvailableTimeSlots(selectedDay.isToday)
    }
    
    var selectedSlot by remember(availableTimeSlots) { 
        mutableStateOf(if (availableTimeSlots.isNotEmpty()) availableTimeSlots.first() else null) 
    }

    // SLOTS STATE LOGIC
    val initialSlots = remember(area.slots) {
        val parts = area.slots.split("/")
        val avail = parts.getOrNull(0)?.trim()?.toIntOrNull() ?: 0
        val total = parts.getOrNull(1)?.trim()?.toIntOrNull() ?: 100
        Pair(avail, total)
    }
    
    var availableCount by remember { mutableStateOf(initialSlots.first) }
    val totalCount = initialSlots.second

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
            HyderabadParkingBottomBar {
                if (availableCount > 0) {
                    val slot = selectedSlot
                    if (slot != null) {
                        availableCount -= 1
                        
                        // Format date for backend (YYYY-MM-DD)
                        val dateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
                        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        val parsedDate = try {
                            dateFormat.parse(selectedDay.date)
                        } catch (e: Exception) {
                            java.util.Date()
                        }
                        val formattedDate = parsedDate?.let { outputFormat.format(it) } ?: selectedDay.date
                        
                        // Pass: area name, day, date (YYYY-MM-DD), date display (dd MMM), time slot
                        onBookNowClick(
                            area.name,
                            selectedDay.day,
                            formattedDate,
                            selectedDay.date,
                            slot.title
                        )
                    }
                }
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

                        HyderabadParkingStatusChip(if (availableCount > 0) "Available" else "Full")
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

            /* ---------- SELECT DAY ---------- */
            Text("Select Day", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(days.size) {
                    val day = days[it]
                    HyderabadDayCard(day, day == selectedDay) { selectedDay = day }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            /* ---------- SELECT TIME ---------- */
            Text("Select Time Slot", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            if (availableTimeSlots.isEmpty()) {
                Text("No slots available for today", color = Color.Red, fontSize = 14.sp)
            } else {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(availableTimeSlots.size) {
                        val slot = availableTimeSlots[it]
                        HyderabadTimeSlotCard(slot, slot == selectedSlot) { selectedSlot = slot }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            /* ---------- INFO ROW 1 ---------- */
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                HyderabadInfoCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.LocalParking,
                    title = "Slots",
                    value = "$availableCount / $totalCount"
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
        }
    }
}

/* ---------- DAY CARD ---------- */

@Composable
fun HyderabadDayCard(day: HyderabadDayItem, selected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(72.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (selected) Color(0xFF1976D2) else Color(0xFFE3F2FD)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = day.day,
                fontWeight = FontWeight.Bold,
                color = if (selected) Color.White else Color.Black
            )
            Text(
                text = day.date,
                fontSize = 12.sp,
                color = if (selected) Color.White else Color.DarkGray
            )
        }
    }
}

/* ---------- TIME SLOT CARD ---------- */

@Composable
fun HyderabadTimeSlotCard(slot: HyderabadTimeSlot, selected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (selected) Color(0xFF2E7D32) else Color(0xFFE8F5E9)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = slot.title,
                fontWeight = FontWeight.Bold,
                color = if (selected) Color.White else Color.Black
            )
            Text(
                text = slot.time,
                fontSize = 12.sp,
                color = if (selected) Color.White else Color.DarkGray
            )
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

/* ---------- STATUS CHIP ---------- */

@Composable
fun HyderabadParkingStatusChip(text: String) {
    val backgroundColor = if (text == "Full") Color(0xFFFFEBEE) else Color(0xFFDFF5EA)
    val textColor = if (text == "Full") Color(0xFFD32F2F) else Color(0xFF2E7D32)

    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(50)
    ) {
        Text(
            text = text,
            color = textColor,
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
