package com.simats.smartpark.model

data class ChennaiBookingResponse(
    val status: String,
    val message: String,
    val booking_id: Int? = null
)

