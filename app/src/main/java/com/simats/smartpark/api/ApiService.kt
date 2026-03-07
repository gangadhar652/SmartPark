package com.simats.smartpark.api

import com.simats.smartpark.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // Authentication
    @POST("login.php")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("signup.php")
    suspend fun signup(@Body request: SignupRequest): Response<SignupResponse>

    // Forgot Password
    @POST("forgot_password.php")
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): Response<ForgotPasswordResponse>

    @POST("verify_otp.php")
    suspend fun verifyOtp(@Body request: VerifyOtpRequest): Response<VerifyOtpResponse>

    @POST("reset_password.php")
    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<ResetPasswordResponse>

    // Chennai Locations
    @GET("chennai_locations.php")
    suspend fun getChennaiLocations(): Response<ChennaiLocationsResponse>

    // Chennai Parking Details
    @GET("chennai_parking_details.php")
    suspend fun getChennaiParkingDetails(@Query("area") area: String): Response<ChennaiParkingDetailsResponse>

    // Chennai Booking
    @POST("chennai_create_booking.php")
    suspend fun createChennaiBooking(@Body request: ChennaiBookingRequest): Response<ChennaiBookingResponse>

    // Hyderabad Booking
    @POST("hyderabad_create_booking.php")
    suspend fun createHyderabadBooking(@Body request: HyderabadBookingRequest): Response<HyderabadBookingResponse>

    // Bangalore Booking
    @POST("bangalore_create_booking.php")
    suspend fun createBangaloreBooking(@Body request: BangaloreBookingRequest): Response<BangaloreBookingResponse>

    // Mumbai Booking
    @POST("mumbai_create_booking.php")
    suspend fun createMumbaiBooking(@Body request: MumbaiBookingRequest): Response<MumbaiBookingResponse>

    // Get All Bookings
    @GET("get_all_bookings.php")
    suspend fun getAllBookings(): Response<AllBookingsResponse>

    // Mumbai EV Booking
    @POST("mumbai_ev_create_booking.php")
    suspend fun createMumbaiEvBooking(@Body request: MumbaiEvBookingRequest): Response<MumbaiEvBookingResponse>

    // Delhi EV Booking
    @POST("delhi_ev_create_booking.php")
    suspend fun createDelhiEvBooking(@Body request: DelhiEvBookingRequest): Response<DelhiEvBookingResponse>

    // Bangalore EV Booking
    @POST("bangalore_ev_create_booking.php")
    suspend fun createBangaloreEvBooking(@Body request: BangaloreEvBookingRequest): Response<BangaloreEvBookingResponse>

    // Pune EV Booking
    @POST("pune_ev_create_booking.php")
    suspend fun createPuneEvBooking(@Body request: PuneEvBookingRequest): Response<PuneEvBookingResponse>

    // Update Profile
    @POST("update_profile.php")
    suspend fun updateProfile(@Body request: UpdateProfileRequest): Response<UpdateProfileResponse>

    // Get User Profile
    @GET("get_user_profile.php")
    suspend fun getUserProfile(@Query("email") email: String): Response<GetUserProfileResponse>
}

