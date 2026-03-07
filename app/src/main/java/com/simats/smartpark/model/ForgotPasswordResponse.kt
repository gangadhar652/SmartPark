package com.simats.smartpark.model

data class ForgotPasswordResponse(
    val status: String,
    val message: String,
    val otp: String? = null, // OTP for testing (only in development)
    val email_sent: Boolean? = null
)

