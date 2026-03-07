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

data class MumbaiParkingArea(
    val name: String,
    val slots: String,
    val evSlots: String,
    val price: String
)

data class MumbaiDayItem(val day: String, val date: String)
data class MumbaiTimeSlot(val title: String, val time: String)

/* ---------- DATE HELPER ---------- */

fun getMumbaiNext7DaysList(): List<MumbaiDayItem> {
    val list = mutableListOf<MumbaiDayItem>()
    val cal = Calendar.getInstance()
    val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())
    val dateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())

    repeat(7) {
        list.add(MumbaiDayItem(dayFormat.format(cal.time), dateFormat.format(cal.time)))
        cal.add(Calendar.DAY_OF_YEAR, 1)
    }
    return list
}

/* ---------- TIME SLOTS ---------- */

val mumbaiTimeSlots = listOf(
    MumbaiTimeSlot("Morning", "6 AM - 12 PM (6 hrs)"),
    MumbaiTimeSlot("Afternoon", "12 PM - 6 PM (6 hrs)"),
    MumbaiTimeSlot("Night", "6 PM - 12 AM (6 hrs)")
)

/* ---------- MAIN SCREEN ---------- */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MumbaiParkingDetailsScreen(
    area: MumbaiParkingArea,
    onBackClick: () -> Unit,
    onBookNowClick: (String, String, String, String, String) -> Unit
) {

    val days = remember { getMumbaiNext7DaysList() }
    var selectedDay by remember { mutableStateOf(days.first()) }
    var selectedSlot by remember { mutableStateOf(mumbaiTimeSlots.first()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mumbai Parking Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            MumbaiParkingBottomBar {
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
                    selectedSlot.title
                )
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
                                text = "${area.name}, Mumbai",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }

                        MumbaiParkingStatusChip("Available")
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
                        Text("1k+ reviews", fontSize = 12.sp, color = Color.Gray)
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
                    MumbaiDayCard(day, day == selectedDay) { selectedDay = day }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            /* ---------- SELECT TIME ---------- */
            Text("Select Time Slot", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(mumbaiTimeSlots.size) {
                    val slot = mumbaiTimeSlots[it]
                    MumbaiTimeSlotCard(slot, slot == selectedSlot) { selectedSlot = slot }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            /* ---------- INFO ROW 1 ---------- */
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MumbaiInfoCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.LocalParking,
                    title = "Slots",
                    value = area.slots
                )
                MumbaiInfoCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.ElectricCar,
                    title = "EV Charging",
                    value = area.evSlots
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            /* ---------- INFO ROW 2 ---------- */
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MumbaiInfoCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Schedule,
                    title = "Price / Hour",
                    value = area.price
                )
                MumbaiInfoCard(
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
fun MumbaiDayCard(day: MumbaiDayItem, selected: Boolean, onClick: () -> Unit) {
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
fun MumbaiTimeSlotCard(slot: MumbaiTimeSlot, selected: Boolean, onClick: () -> Unit) {
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
fun MumbaiInfoCard(
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
fun MumbaiParkingStatusChip(text: String) {
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
fun MumbaiParkingBottomBar(onBookNowClick: () -> Unit) {
    Button(
        onClick = onBookNowClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text("Book Now")
    }
}

/* ---------- MUMBAI PARKING DATA ---------- */

val BANDRA = MumbaiParkingArea("Bandra", "90 / 200", "20 Available", "₹80")
val ANDHERI = MumbaiParkingArea("Andheri", "75 / 180", "15 Available", "₹75")
val POWAI = MumbaiParkingArea("Powai", "60 / 150", "12 Available", "₹70")
val WORLI = MumbaiParkingArea("Worli", "55 / 140", "10 Available", "₹85")
val COLABA = MumbaiParkingArea("Colaba", "45 / 120", "8 Available", "₹90")
