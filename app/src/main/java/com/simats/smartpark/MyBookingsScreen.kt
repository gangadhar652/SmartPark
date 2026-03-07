package com.simats.smartpark

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
import com.simats.smartpark.api.ApiClient
import com.simats.smartpark.model.BookingItem
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MyBookingsScreen(
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onEvClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("All", "Parking", "EV Charging")
    
    var isLoading by remember { mutableStateOf(true) }
    var bookings by remember { mutableStateOf<List<BookingItem>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                isLoading = true
                val response = ApiClient.apiService.getAllBookings()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status == "success") {
                        bookings = body.bookings
                        errorMessage = null
                    } else {
                        errorMessage = "Failed to load bookings"
                    }
                } else {
                    errorMessage = "Server error: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage = "Error: ${e.localizedMessage ?: e.message}"
            } finally {
                isLoading = false
            }
        }
    }

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
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator()
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Loading bookings...", color = Color.Gray)
                        }
                    }
                }
                errorMessage != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Default.Error,
                                contentDescription = null,
                                tint = Color.Red,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = errorMessage ?: "Error loading bookings",
                                color = Color.Red
                            )
                        }
                    }
                }
                bookings.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Default.EventBusy,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "No bookings found",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Make a booking to see it here",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
                else -> {
                    val filteredBookings = when (selectedTab) {
                        0 -> bookings // All
                        1 -> bookings.filter { !it.city.contains("EV", ignoreCase = true) } // Parking
                        2 -> bookings.filter { it.city.contains("EV", ignoreCase = true) } // EV Charging
                        else -> bookings
                    }

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(filteredBookings.size) { index ->
                            val booking = filteredBookings[index]
                            val isEvBooking = booking.city.contains("EV", ignoreCase = true)
                            BookingCard(
                                title = if (isEvBooking) "${booking.area_name} EV Charging" else "${booking.area_name} Parking",
                                location = "${booking.area_name}, ${booking.city.replace(" EV", "")}",
                                date = formatBookingDate(booking.booking_date_display, booking.booking_day),
                                duration = "${booking.duration_hours} hours",
                                slot = booking.time_slot,
                                price = calculatePrice(booking.duration_hours),
                                status = if (booking.status == "confirmed") "Active" else booking.status,
                                isEv = isEvBooking
                            )
                        }
                    }
                }
            }
        }
    }
}

/* ---------- HELPER FUNCTIONS ---------- */

fun formatBookingDate(dateDisplay: String, day: String): String {
    return try {
        val today = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
        val bookingDate = dateFormat.parse(dateDisplay)
        
        if (bookingDate != null) {
            val bookingCal = Calendar.getInstance()
            bookingCal.time = bookingDate
            // Set year to current year for comparison
            bookingCal.set(Calendar.YEAR, today.get(Calendar.YEAR))
            
            val todayDate = today.get(Calendar.DAY_OF_MONTH)
            val todayMonth = today.get(Calendar.MONTH)
            val bookingDateNum = bookingCal.get(Calendar.DAY_OF_MONTH)
            val bookingMonth = bookingCal.get(Calendar.MONTH)
            
            when {
                todayDate == bookingDateNum && todayMonth == bookingMonth -> "Today"
                (todayDate - 1 == bookingDateNum && todayMonth == bookingMonth) || 
                (todayDate == 1 && bookingDateNum == today.getActualMaximum(Calendar.DAY_OF_MONTH) && 
                 todayMonth - 1 == bookingMonth) -> "Yesterday"
                else -> "$day, $dateDisplay"
            }
        } else {
            "$day, $dateDisplay"
        }
    } catch (e: Exception) {
        "$day, $dateDisplay"
    }
}

fun calculatePrice(durationHours: Int): String {
    // Assuming ₹50 per hour as base price
    val price = durationHours * 50
    return "₹$price"
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
    val background = when (status) {
        "Active" -> Color(0xFFE8F5E9)
        "Completed" -> Color(0xFFE3F2FD)
        else -> Color(0xFFE0E0E0)
    }
    val textColor = when (status) {
        "Active" -> Color(0xFF2E7D32)
        "Completed" -> Color(0xFF1976D2)
        else -> Color.DarkGray
    }

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
            onClick = onProfileClick,
            icon = { Icon(Icons.Default.Person, null) },
            label = { Text("Profile") }
        )
    }
}
