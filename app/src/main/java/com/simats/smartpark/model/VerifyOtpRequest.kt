package com.simats.smartpark.model

data class VerifyOtpRequest(
    val email: String,
    val otp: String
)


