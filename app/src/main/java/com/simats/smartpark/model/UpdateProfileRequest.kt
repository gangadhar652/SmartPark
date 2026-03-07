package com.simats.smartpark.model

data class UpdateProfileRequest(
    val email: String,
    val full_name: String,
    val phone: String,
    val vehicle_number: String? = null
)



