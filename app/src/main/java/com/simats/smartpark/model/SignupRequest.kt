package com.simats.smartpark.model

data class SignupRequest(
    val full_name: String,
    val email: String,
    val phone: String,
    val vehicle_number: String,
    val password: String
)






