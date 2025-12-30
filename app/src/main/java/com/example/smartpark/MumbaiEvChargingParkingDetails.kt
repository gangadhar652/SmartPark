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

/* ---------- DATA MODEL ---------- */

data class MumbaiEvParkingArea(
    val name: String,
    val power: String,
    val connector: String,
    val price: String
)

data class MumbaiEvDayItem(val day: String, val date: String)
data class MumbaiEvTimeSlot(val title: String, val time: String)

/* ---------- DATE HELPER ---------- */

fun getMumbaiEvNext7DaysList(): List<MumbaiEvDayItem> {
    val list = mutableListOf<MumbaiEvDayItem>()
    val cal = Calendar.getInstance()
    val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())
    val dateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())

    repeat(7) {
        list.add(MumbaiEvDayItem(dayFormat.format(cal.time), dateFormat.format(cal.time)))
        cal.add(Calendar.DAY_OF_YEAR, 1)
    }
    return list
}

/* ---------- TIME SLOTS ---------- */

val mumbaiEvTimeSlots = listOf(
    MumbaiEvTimeSlot("Morning", "6 AM - 12 PM (6 hrs)"),
    MumbaiEvTimeSlot("Afternoon", "12 PM - 6 PM (6 hrs)"),
    MumbaiEvTimeSlot("Night", "6 PM - 12 AM (6 hrs)")
)

/* ---------- MAIN SCREEN ---------- */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MumbaiEvParkingDetailsScreen(
    area: MumbaiEvParkingArea,
    onBackClick: () -> Unit,
    onBookNowClick: () -> Unit
) {
    val days = remember { getMumbaiEvNext7DaysList() }
    var selectedDay by remember { mutableStateOf(days.first()) }
    var selectedSlot by remember { mutableStateOf(mumbaiEvTimeSlots.first()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mumbai EV Parking Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            MumbaiEvParkingBottomBar(onBookNowClick)
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
                                text = "${area.name} EV Parking",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${area.name}, Mumbai",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }

                        MumbaiEvStatusChip("Available")
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
                    MumbaiEvDayCard(day, day == selectedDay) { selectedDay = day }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            /* ---------- SELECT TIME ---------- */
            Text("Select Time Slot", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(mumbaiEvTimeSlots.size) {
                    val slot = mumbaiEvTimeSlots[it]
                    MumbaiEvTimeSlotCard(slot, slot == selectedSlot) { selectedSlot = slot }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            /* ---------- INFO ROW 1 ---------- */
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MumbaiEvInfoCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Bolt,
                    title = "Power",
                    value = area.power
                )
                MumbaiEvInfoCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.ElectricCar,
                    title = "Connector",
                    value = area.connector
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            /* ---------- INFO ROW 2 ---------- */
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MumbaiEvInfoCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.CurrencyRupee,
                    title = "Price / kWh",
                    value = area.price
                )
                MumbaiEvInfoCard(
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
fun MumbaiEvDayCard(day: MumbaiEvDayItem, selected: Boolean, onClick: () -> Unit) {
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
fun MumbaiEvTimeSlotCard(slot: MumbaiEvTimeSlot, selected: Boolean, onClick: () -> Unit) {
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
fun MumbaiEvInfoCard(
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
            Icon(icon, contentDescription = null, tint = Color(0xFF2E7D32))
            Spacer(modifier = Modifier.height(6.dp))
            Text(title, fontSize = 12.sp, color = Color.Gray)
            Text(value, fontWeight = FontWeight.Bold)
        }
    }
}

/* ---------- STATUS CHIP ---------- */

@Composable
fun MumbaiEvStatusChip(text: String) {
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
fun MumbaiEvParkingBottomBar(onBookNowClick: () -> Unit) {
    Button(
        onClick = onBookNowClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text("Book Now")
    }
}

/* ---------- MUMBAI EV PARKING DATA ---------- */

val BKC_EV = MumbaiEvParkingArea(
    name = "Bandra Kurla Complex",
    power = "120 kW",
    connector = "CCS / CHAdeMO",
    price = "₹14"
)

val POWAI_EV = MumbaiEvParkingArea(
    name = "Powai Lake Charging",
    power = "100 kW",
    connector = "CCS",
    price = "₹13"
)

val ANDHERI_EV = MumbaiEvParkingArea(
    name = "Andheri Metro",
    power = "60 kW",
    connector = "Type 2",
    price = "₹9"
)

val WORLI_EV = MumbaiEvParkingArea(
    name = "Worli Sea Link Plaza",
    power = "140 kW",
    connector = "CCS / CHAdeMO",
    price = "₹15"
)
