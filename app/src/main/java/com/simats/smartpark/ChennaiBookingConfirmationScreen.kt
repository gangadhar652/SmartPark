package com.simats.smartpark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.simats.smartpark.model.ChennaiBookingRequest
import com.google.gson.Gson
import kotlinx.coroutines.launch

@Composable
fun ChennaiBookingConfirmationScreen(
    areaName: String,
    bookingDay: String,
    bookingDate: String,
    bookingDateDisplay: String,
    timeSlot: String,
    vehicleNumber: String = "TN 01 AB 1234",
    onBackHomeClick: () -> Unit,
    onViewBookingsClick: () -> Unit
) {
    var isLoading by remember { mutableStateOf(true) }
    var bookingSuccess by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val durationHours = when (timeSlot) {
                    "Morning" -> 6
                    "Afternoon" -> 6
                    "Night" -> 6
                    else -> 6
                }

                val request = ChennaiBookingRequest(
                    area_name = areaName,
                    booking_day = bookingDay,
                    booking_date = bookingDate,
                    booking_date_display = bookingDateDisplay,
                    time_slot = timeSlot,
                    duration_hours = durationHours,
                    vehicle_number = vehicleNumber
                )

                // Validate required fields before API call
                if (areaName.isEmpty() || bookingDay.isEmpty() || bookingDate.isEmpty() || 
                    bookingDateDisplay.isEmpty() || timeSlot.isEmpty()) {
                    errorMessage = "Missing booking information. Please go back and select day/time."
                    isLoading = false
                    return@launch
                }

                val response = ApiClient.apiService.createChennaiBooking(request)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status == "success") {
                        bookingSuccess = true
                        isLoading = false
                    } else {
                        // Try to read error body
                        val errorBody = try {
                            response.errorBody()?.string() ?: "No error body"
                        } catch (e: Exception) {
                            "Could not read error: ${e.message}"
                        }
                        errorMessage = body?.message ?: "Booking failed. Response: $errorBody"
                        isLoading = false
                    }
                } else {
                    // Read error body for non-successful responses
                    val errorBody = try {
                        response.errorBody()?.string() ?: "No error details"
                    } catch (e: Exception) {
                        "Could not read error: ${e.message}"
                    }
                    
                    // Try to parse error as JSON
                    val errorMsg = try {
                        val errorJson = Gson().fromJson(errorBody, Map::class.java)
                        errorJson["message"]?.toString() ?: errorBody
                    } catch (e: Exception) {
                        errorBody
                    }
                    
                    errorMessage = "Server error (${response.code()}): $errorMsg"
                    isLoading = false
                }
            } catch (e: com.google.gson.JsonSyntaxException) {
                errorMessage = "JSON parsing error. Please check server response format."
                isLoading = false
            } catch (e: java.net.UnknownHostException) {
                errorMessage = "Cannot connect to server.\n\nCheck:\n1. XAMPP Apache is RUNNING\n2. Test: http://localhost/smartpark/chennai_create_booking.php"
                isLoading = false
            } catch (e: java.net.SocketTimeoutException) {
                errorMessage = "Connection timeout.\n\nCheck XAMPP Apache is running."
                isLoading = false
            } catch (e: Exception) {
                errorMessage = "Error: ${e.localizedMessage ?: e.message}\n\nType: ${e.javaClass.simpleName}"
                isLoading = false
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        /* ---------- SUCCESS ICON ---------- */
        Box(
            modifier = Modifier
                .size(90.dp)
                .background(Color(0xFFDFF5EA), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Check,
                contentDescription = null,
                tint = Color(0xFF2E7D32),
                modifier = Modifier.size(42.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Booking Confirmed!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Your parking slot is reserved",
            fontSize = 13.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        /* ---------- QR PLACEHOLDER ---------- */
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF4F7FB))
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(64.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = if (isLoading) "Processing booking..." else "Booking confirmed successfully!",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
            ) {
                Text(
                    text = errorMessage ?: "Error",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        /* ---------- BOOKING DETAILS ---------- */
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                BookingDetailRow(
                    icon = Icons.Default.LocationOn,
                    title = "Location",
                    value = areaName
                )

                Divider(modifier = Modifier.padding(vertical = 10.dp))

                BookingDetailRow(
                    icon = Icons.Default.CalendarToday,
                    title = "Date",
                    value = "$bookingDay, $bookingDateDisplay"
                )

                Divider(modifier = Modifier.padding(vertical = 10.dp))

                BookingDetailRow(
                    icon = Icons.Default.Schedule,
                    title = "Time Slot",
                    value = timeSlot
                )

                Divider(modifier = Modifier.padding(vertical = 10.dp))

                BookingDetailRow(
                    icon = Icons.Default.Schedule,
                    title = "Duration",
                    value = "6 hours"
                )

                Divider(modifier = Modifier.padding(vertical = 10.dp))

                BookingDetailRow(
                    icon = Icons.Default.DirectionsCar,
                    title = "Vehicle",
                    value = vehicleNumber
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        /* ---------- ACTIONS ---------- */
        Button(
            onClick = onViewBookingsClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("View My Bookings")
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onBackHomeClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Back to Home")
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

/* ---------- DETAIL ROW ---------- */

@Composable
fun BookingDetailRow(
    icon: ImageVector,
    title: String,
    value: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {

        Box(
            modifier = Modifier
                .size(36.dp)
                .background(Color(0xFFE3F2FD), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = Color(0xFF1976D2),
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(title, fontSize = 12.sp, color = Color.Gray)
            Text(value, fontWeight = FontWeight.Bold)
        }
    }
}
