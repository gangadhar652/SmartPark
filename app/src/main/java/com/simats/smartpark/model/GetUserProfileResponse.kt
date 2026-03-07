package com.simats.smartpark.model

data class GetUserProfileResponse(
    val status: String,
    val message: String? = null,
    val data: UserProfileData? = null
)

data class UserProfileData(
    val full_name: String,
    val email: String,
    val phone: String,
    val vehicle_number: String? = null,
    val bookings_count: Int = 0,
    val points: Int = 0,
    val rating: String = "4.9"
)




