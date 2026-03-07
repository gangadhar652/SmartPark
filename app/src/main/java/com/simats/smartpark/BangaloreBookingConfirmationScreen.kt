package com.simats.smartpark

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.simats.smartpark.model.BangaloreBookingRequest
import com.google.gson.Gson
import kotlinx.coroutines.launch

@Composable
fun BangaloreBookingConfirmationScreen(
    areaName: String,
    bookingDay: String,
    bookingDate: String,
    bookingDateDisplay: String,
    timeSlot: String,
    vehicleNumber: String = "KA 01 AB 1234", // Default for Bangalore
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

                val request = BangaloreBookingRequest(
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

                val response = ApiClient.apiService.createBangaloreBooking(request)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status == "success") {
                        bookingSuccess = true
                        isLoading = false
                    } else {
                        val errorBody = try {
                            response.errorBody()?.string() ?: "No error body"
                        } catch (e: Exception) {
                            "Could not read error: ${e.message}"
                        }
                        errorMessage = body?.message ?: "Booking failed. Response: $errorBody"
                        isLoading = false
                    }
                } else {
                    val errorBody = try {
                        response.errorBody()?.string() ?: "No error details"
                    } catch (e: Exception) {
                        "Could not read error: ${e.message}"
                    }

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
                errorMessage = "Cannot connect to server.\n\nCheck:\n1. XAMPP Apache is RUNNING\n2. Test: http://localhost/smartpark/bangalore_create_booking.php"
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
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        /* ---------- SUCCESS ICON ---------- */
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color(0xFFE8F5E9), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Check,
                contentDescription = null,
                tint = Color(0xFF2E7D32),
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Booking Confirmed!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Your parking slot is reserved",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        /* ---------- STATUS CARD (Loading/Success/Error) ---------- */
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FB))
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Processing booking...",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                } else if (bookingSuccess) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Booking confirmed successfully!",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                } else {
                    Icon(
                        Icons.Default.Error,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Booking failed",
                        fontSize = 12.sp,
                        color = Color.Red
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

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
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F1F5))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                BangaloreBookingDetailRow(
                    icon = Icons.Default.LocationOn,
                    title = "Location",
                    value = areaName
                )

                Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color.LightGray)

                BangaloreBookingDetailRow(
                    icon = Icons.Default.CalendarToday,
                    title = "Date",
                    value = "$bookingDay, $bookingDateDisplay"
                )

                Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color.LightGray)

                BangaloreBookingDetailRow(
                    icon = Icons.Default.Schedule,
                    title = "Time Slot",
                    value = timeSlot
                )

                Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color.LightGray)

                BangaloreBookingDetailRow(
                    icon = Icons.Default.Schedule,
                    title = "Duration",
                    value = "6 hours"
                )

                Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color.LightGray)

                BangaloreBookingDetailRow(
                    icon = Icons.Default.DirectionsCar,
                    title = "Vehicle",
                    value = vehicleNumber
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        /* ---------- ACTIONS ---------- */
        Button(
            onClick = onViewBookingsClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6750A4))
        ) {
            Text("View My Bookings", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onBackHomeClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Text("Back to Home", fontSize = 16.sp, color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun BangaloreBookingDetailRow(
    icon: ImageVector,
    title: String,
    value: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {

        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFFE3F2FD), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = Color(0xFF1976D2),
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(title, fontSize = 12.sp, color = Color.Gray)
            Text(value, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}





