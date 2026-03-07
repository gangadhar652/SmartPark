package com.simats.smartpark.model

data class HyderabadBookingRequest(
    val area_name: String,
    val booking_day: String,
    val booking_date: String,
    val booking_date_display: String,
    val time_slot: String,
    val duration_hours: Int = 6,
    val vehicle_number: String
)





