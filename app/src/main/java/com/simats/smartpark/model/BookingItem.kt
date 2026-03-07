package com.simats.smartpark.model

data class BookingItem(
    val id: Int,
    val city: String,
    val area_name: String,
    val booking_day: String,
    val booking_date: String,
    val booking_date_display: String,
    val time_slot: String,
    val duration_hours: Int,
    val vehicle_number: String,
    val status: String,
    val created_at: String
)

data class AllBookingsResponse(
    val status: String,
    val bookings: List<BookingItem>,
    val count: Int
)




