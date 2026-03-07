package com.simats.smartpark.NavGraph

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.simats.smartpark.*

/* ---------------- ROUTES ---------------- */

object Routes {

    const val SPLASH = "splash"
    const val SUBSCRIPTION = "subscription"
    const val MEMBERSHIP_PLANS = "membership_plans"
    const val MY_VEHICLES = "my_vehicles"

    const val ONBOARDING_1 = "onboarding_1"
    const val ONBOARDING_2 = "onboarding_2"
    const val ONBOARDING_3 = "onboarding_3"

    const val LOGIN = "login"
    const val SIGNUP = "signup"
    const val FORGOT_PASSWORD = "forgot_password"
    const val VERIFY_OTP = "verify_otp"
    const val RESET_PASSWORD = "reset_password"

    const val HOME = "home"
    const val EV_CHARGING = "ev_charging"
    const val PROFILE = "profile"
    const val EDIT_PROFILE = "edit_profile"
    const val MY_BOOKINGS = "my_bookings"

    // Parking Cities
    const val CHENNAI_LOCATIONS = "chennai_locations"
    const val HYDERABAD_LOCATIONS = "hyderabad_locations"
    const val BANGALORE_LOCATIONS = "bangalore_locations"
    const val MUMBAI_LOCATIONS = "mumbai_locations"

    // Parking Details
    const val PARKING_DETAILS = "parking_details"
    const val HYDERABAD_PARKING_DETAILS = "hyderabad_parking_details"
    const val BANGALORE_PARKING_DETAILS = "bangalore_parking_details"
    const val MUMBAI_PARKING_DETAILS = "mumbai_parking_details"

    // Booking Confirmation
    const val BOOKING_CONFIRMATION = "booking_confirmation"

    // EV Charging
    const val MUMBAI_EV_CHARGERS = "mumbai_ev_chargers"
    const val DELHI_EV_CHARGERS = "delhi_ev_chargers"
    const val BANGALORE_EV_CHARGERS = "bangalore_ev_chargers"
    const val PUNE_EV_CHARGERS = "pune_ev_chargers"

    const val MUMBAI_EV_DETAILS = "mumbai_ev_details"
    const val DELHI_EV_DETAILS = "delhi_ev_details"
    const val BANGALORE_EV_DETAILS = "bangalore_ev_details"
    const val PUNE_EV_DETAILS = "pune_ev_details"
}

/* ---------------- NAV GRAPH ---------------- */

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()
    var profileRefreshKey by remember { mutableStateOf(0) }

    // State for forgot password flow
    var forgotPasswordEmail by remember { mutableStateOf("") }
    var verifiedOtp by remember { mutableStateOf("") }

    // Selected parking holders
    var selectedHyderabadArea by remember { mutableStateOf<HyderabadParkingArea?>(null) }
    var selectedBangaloreArea by remember { mutableStateOf<BangaloreParkingArea?>(null) }
    var selectedMumbaiArea by remember { mutableStateOf<MumbaiParkingArea?>(null) }

    var selectedMumbaiEvArea by remember { mutableStateOf<MumbaiEvParkingArea?>(null) }
    var selectedDelhiEvArea by remember { mutableStateOf<DelhiEvParkingArea?>(null) }
    var selectedBangaloreEvArea by remember { mutableStateOf<BangaloreEvParkingArea?>(null) }
    var selectedPuneEvArea by remember { mutableStateOf<PuneEvParkingArea?>(null) }

    // Chennai booking data
    var chennaiBookingArea by remember { mutableStateOf<String>("") }
    var chennaiBookingDay by remember { mutableStateOf<String>("") }
    var chennaiBookingDate by remember { mutableStateOf<String>("") }
    var chennaiBookingDateDisplay by remember { mutableStateOf<String>("") }
    var chennaiBookingTimeSlot by remember { mutableStateOf<String>("") }

    // Hyderabad booking data
    var hyderabadBookingArea by remember { mutableStateOf<String>("") }
    var hyderabadBookingDay by remember { mutableStateOf<String>("") }
    var hyderabadBookingDate by remember { mutableStateOf<String>("") }
    var hyderabadBookingDateDisplay by remember { mutableStateOf<String>("") }
    var hyderabadBookingTimeSlot by remember { mutableStateOf<String>("") }

    // Bangalore booking data
    var bangaloreBookingArea by remember { mutableStateOf<String>("") }
    var bangaloreBookingDay by remember { mutableStateOf<String>("") }
    var bangaloreBookingDate by remember { mutableStateOf<String>("") }
    var bangaloreBookingDateDisplay by remember { mutableStateOf<String>("") }
    var bangaloreBookingTimeSlot by remember { mutableStateOf<String>("") }

    // Mumbai booking data
    var mumbaiBookingArea by remember { mutableStateOf<String>("") }
    var mumbaiBookingDay by remember { mutableStateOf<String>("") }
    var mumbaiBookingDate by remember { mutableStateOf<String>("") }
    var mumbaiBookingDateDisplay by remember { mutableStateOf<String>("") }
    var mumbaiBookingTimeSlot by remember { mutableStateOf<String>("") }

    // Mumbai EV booking data
    var mumbaiEvBookingArea by remember { mutableStateOf<String>("") }
    var mumbaiEvBookingDay by remember { mutableStateOf<String>("") }
    var mumbaiEvBookingDate by remember { mutableStateOf<String>("") }
    var mumbaiEvBookingDateDisplay by remember { mutableStateOf<String>("") }
    var mumbaiEvBookingTimeSlot by remember { mutableStateOf<String>("") }

    // Delhi EV booking data
    var delhiEvBookingArea by remember { mutableStateOf<String>("") }
    var delhiEvBookingDay by remember { mutableStateOf<String>("") }
    var delhiEvBookingDate by remember { mutableStateOf<String>("") }
    var delhiEvBookingDateDisplay by remember { mutableStateOf<String>("") }
    var delhiEvBookingTimeSlot by remember { mutableStateOf<String>("") }

    // Bangalore EV booking data
    var bangaloreEvBookingArea by remember { mutableStateOf<String>("") }
    var bangaloreEvBookingDay by remember { mutableStateOf<String>("") }
    var bangaloreEvBookingDate by remember { mutableStateOf<String>("") }
    var bangaloreEvBookingDateDisplay by remember { mutableStateOf<String>("") }
    var bangaloreEvBookingTimeSlot by remember { mutableStateOf<String>("") }

    // Pune EV booking data
    var puneEvBookingArea by remember { mutableStateOf<String>("") }
    var puneEvBookingDay by remember { mutableStateOf<String>("") }
    var puneEvBookingDate by remember { mutableStateOf<String>("") }
    var puneEvBookingDateDisplay by remember { mutableStateOf<String>("") }
    var puneEvBookingTimeSlot by remember { mutableStateOf<String>("") }


    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {

        /* ---------------- SPLASH ---------------- */
        composable(Routes.SPLASH) {
            SplashScreen {
                navController.navigate(Routes.SUBSCRIPTION) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            }
        }

        /* ---------------- SUBSCRIPTION ---------------- */
        composable(Routes.SUBSCRIPTION) {
            SubscriptionScreen(
                onContinue = {
                    navController.navigate(Routes.ONBOARDING_1) {
                        popUpTo(Routes.SUBSCRIPTION) { inclusive = true }
                    }
                }
            )
        }

        /* ---------------- MEMBERSHIP PLANS (FROM PROFILE) ---------------- */
        composable(Routes.MEMBERSHIP_PLANS) {
            SubscriptionScreen(
                onContinue = {
                    navController.popBackStack()
                }
            )
        }

        /* ---------------- MY VEHICLES ---------------- */
        composable(Routes.MY_VEHICLES) {
            MyVehiclesScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        /* ---------------- ONBOARDING ---------------- */
        composable(Routes.ONBOARDING_1) {
            OnboardingScreen(
                onNextClick = { navController.navigate(Routes.ONBOARDING_2) },
                onSkipClick = { navController.navigate(Routes.ONBOARDING_3) }
            )
        }

        composable(Routes.ONBOARDING_2) {
            OnboardingEvChargingScreen(
                onNextClick = { navController.navigate(Routes.ONBOARDING_3) },
                onSkipClick = { navController.navigate(Routes.ONBOARDING_3) }
            )
        }

        composable(Routes.ONBOARDING_3) {
            OnboardingReadyScreen {
                navController.navigate(Routes.LOGIN) // ✅ BYPASS PERMISSIONS
            }
        }

        /* ---------------- LOGIN ---------------- */
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginClick = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onForgotPasswordClick = { navController.navigate(Routes.FORGOT_PASSWORD) },
                onSignUpClick = { navController.navigate(Routes.SIGNUP) }
            )
        }

        /* ---------------- FORGOT PASSWORD ---------------- */
        composable(Routes.FORGOT_PASSWORD) {
            ForgotPasswordScreen(
                onBackClick = { navController.popBackStack() },
                onContinueClick = { email ->
                    forgotPasswordEmail = email
                    navController.navigate(Routes.VERIFY_OTP)
                }
            )
        }

        /* ---------------- VERIFY OTP ---------------- */
        composable(Routes.VERIFY_OTP) {
            VerifyOtpScreen(
                onBackClick = { navController.popBackStack() },
                onVerifyClick = { email, otp ->
                    verifiedOtp = otp
                    navController.navigate(Routes.RESET_PASSWORD)
                },
                email = forgotPasswordEmail
            )
        }

        /* ---------------- RESET PASSWORD ---------------- */
        composable(Routes.RESET_PASSWORD) {
            ResetPasswordScreen(
                onBackClick = { navController.popBackStack() },
                onSuccessClick = {
                    // Reset state
                    forgotPasswordEmail = ""
                    verifiedOtp = ""
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                email = forgotPasswordEmail,
                otp = verifiedOtp
            )
        }

        /* ---------------- SIGNUP ---------------- */
        composable(Routes.SIGNUP) {
            SignUpScreen(
                onSignUpClick = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.SIGNUP) { inclusive = true }
                    }
                },
                onLoginClick = { navController.popBackStack() }
            )
        }

        /* ---------------- HOME ---------------- */
        composable(Routes.HOME) {
            FindParkingScreen(
                onHomeClick = {},
                onChennaiClick = { navController.navigate(Routes.CHENNAI_LOCATIONS) },
                onHyderabadClick = { navController.navigate(Routes.HYDERABAD_LOCATIONS) },
                onBangaloreClick = { navController.navigate(Routes.BANGALORE_LOCATIONS) },
                onMumbaiClick = { navController.navigate(Routes.MUMBAI_LOCATIONS) },
                onEvClick = { navController.navigate(Routes.EV_CHARGING) },
                onProfileClick = { navController.navigate(Routes.PROFILE) },
                onBookingsClick = { navController.navigate(Routes.MY_BOOKINGS) }
            )
        }

        /* ---------------- CHENNAI PARKING ---------------- */
        composable(Routes.CHENNAI_LOCATIONS) {
            ChennaiLocationsScreen(
                onBack = { navController.popBackStack() },
                onTNagarClick = { navController.navigate("${Routes.PARKING_DETAILS}/t_nagar") },
                onAnnaNagarClick = { navController.navigate("${Routes.PARKING_DETAILS}/anna_nagar") },
                onVelacheryClick = { navController.navigate("${Routes.PARKING_DETAILS}/velachery") },
                onAdyarClick = { navController.navigate("${Routes.PARKING_DETAILS}/adyar") },
                onMylaporeClick = { navController.navigate("${Routes.PARKING_DETAILS}/mylapore") }
            )
        }

        composable("${Routes.PARKING_DETAILS}/{areaId}") { backStackEntry ->
            val areaId = backStackEntry.arguments?.getString("areaId")

            val area = when (areaId) {
                "t_nagar" -> T_NAGAR
                "anna_nagar" -> ANNA_NAGAR
                "velachery" -> VELACHERY
                "adyar" -> ADYAR
                "mylapore" -> MYLAPORE
                else -> T_NAGAR
            }

            ParkingDetailsScreen(
                area = area,
                onBackClick = { navController.popBackStack() },
                onBookNowClick = { areaName, day, date, dateDisplay, timeSlot ->
                    chennaiBookingArea = areaName
                    chennaiBookingDay = day
                    chennaiBookingDate = date
                    chennaiBookingDateDisplay = dateDisplay
                    chennaiBookingTimeSlot = timeSlot
                    navController.navigate(Routes.BOOKING_CONFIRMATION)
                }
            )
        }

        /* ---------------- BOOKING CONFIRMATION ---------------- */
        composable(Routes.BOOKING_CONFIRMATION) {
            // Priority: Check which city has data
            when {
                chennaiBookingArea.isNotEmpty() -> {
                    ChennaiBookingConfirmationScreen(
                        areaName = chennaiBookingArea,
                        bookingDay = chennaiBookingDay,
                        bookingDate = chennaiBookingDate,
                        bookingDateDisplay = chennaiBookingDateDisplay,
                        timeSlot = chennaiBookingTimeSlot,
                        onBackHomeClick = {
                            navController.navigate(Routes.HOME) { popUpTo(Routes.HOME) { inclusive = true } }
                            chennaiBookingArea = ""
                        },
                        onViewBookingsClick = {
                            navController.navigate(Routes.MY_BOOKINGS) { popUpTo(Routes.HOME) }
                            chennaiBookingArea = ""
                        }
                    )
                }
                hyderabadBookingArea.isNotEmpty() -> {
                    HyderabadBookingConfirmationScreen(
                        areaName = hyderabadBookingArea,
                        bookingDay = hyderabadBookingDay,
                        bookingDate = hyderabadBookingDate,
                        bookingDateDisplay = hyderabadBookingDateDisplay,
                        timeSlot = hyderabadBookingTimeSlot,
                        onBackHomeClick = {
                            navController.navigate(Routes.HOME) { popUpTo(Routes.HOME) { inclusive = true } }
                            hyderabadBookingArea = ""
                        },
                        onViewBookingsClick = {
                            navController.navigate(Routes.MY_BOOKINGS) { popUpTo(Routes.HOME) }
                            hyderabadBookingArea = ""
                        }
                    )
                }
                bangaloreBookingArea.isNotEmpty() -> {
                    BangaloreBookingConfirmationScreen(
                        areaName = bangaloreBookingArea,
                        bookingDay = bangaloreBookingDay,
                        bookingDate = bangaloreBookingDate,
                        bookingDateDisplay = bangaloreBookingDateDisplay,
                        timeSlot = bangaloreBookingTimeSlot,
                        onBackHomeClick = {
                            navController.navigate(Routes.HOME) { popUpTo(Routes.HOME) { inclusive = true } }
                            bangaloreBookingArea = ""
                        },
                        onViewBookingsClick = {
                            navController.navigate(Routes.MY_BOOKINGS) { popUpTo(Routes.HOME) }
                            bangaloreBookingArea = ""
                        }
                    )
                }
                mumbaiBookingArea.isNotEmpty() -> {
                    MumbaiBookingConfirmationScreen(
                        areaName = mumbaiBookingArea,
                        bookingDay = mumbaiBookingDay,
                        bookingDate = mumbaiBookingDate,
                        bookingDateDisplay = mumbaiBookingDateDisplay,
                        timeSlot = mumbaiBookingTimeSlot,
                        onBackHomeClick = {
                            navController.navigate(Routes.HOME) { popUpTo(Routes.HOME) { inclusive = true } }
                            mumbaiBookingArea = ""
                        },
                        onViewBookingsClick = {
                            navController.navigate(Routes.MY_BOOKINGS) { popUpTo(Routes.HOME) }
                            mumbaiBookingArea = ""
                        }
                    )
                }
                mumbaiEvBookingArea.isNotEmpty() -> {
                    MumbaiEvBookingConfirmationScreen(
                        areaName = mumbaiEvBookingArea,
                        bookingDay = mumbaiEvBookingDay,
                        bookingDate = mumbaiEvBookingDate,
                        bookingDateDisplay = mumbaiEvBookingDateDisplay,
                        timeSlot = mumbaiEvBookingTimeSlot,
                        onBackHomeClick = {
                            navController.navigate(Routes.HOME) { popUpTo(Routes.HOME) { inclusive = true } }
                            mumbaiEvBookingArea = ""
                        },
                        onViewBookingsClick = {
                            navController.navigate(Routes.MY_BOOKINGS) { popUpTo(Routes.HOME) }
                            mumbaiEvBookingArea = ""
                        }
                    )
                }
                delhiEvBookingArea.isNotEmpty() -> {
                    DelhiEvBookingConfirmationScreen(
                        areaName = delhiEvBookingArea,
                        bookingDay = delhiEvBookingDay,
                        bookingDate = delhiEvBookingDate,
                        bookingDateDisplay = delhiEvBookingDateDisplay,
                        timeSlot = delhiEvBookingTimeSlot,
                        onBackHomeClick = {
                            navController.navigate(Routes.HOME) { popUpTo(Routes.HOME) { inclusive = true } }
                            delhiEvBookingArea = ""
                        },
                        onViewBookingsClick = {
                            navController.navigate(Routes.MY_BOOKINGS) { popUpTo(Routes.HOME) }
                            delhiEvBookingArea = ""
                        }
                    )
                }
                bangaloreEvBookingArea.isNotEmpty() -> {
                    BangaloreEvBookingConfirmationScreen(
                        areaName = bangaloreEvBookingArea,
                        bookingDay = bangaloreEvBookingDay,
                        bookingDate = bangaloreEvBookingDate,
                        bookingDateDisplay = bangaloreEvBookingDateDisplay,
                        timeSlot = bangaloreEvBookingTimeSlot,
                        onBackHomeClick = {
                            navController.navigate(Routes.HOME) { popUpTo(Routes.HOME) { inclusive = true } }
                            bangaloreEvBookingArea = ""
                        },
                        onViewBookingsClick = {
                            navController.navigate(Routes.MY_BOOKINGS) { popUpTo(Routes.HOME) }
                            bangaloreEvBookingArea = ""
                        }
                    )
                }
                puneEvBookingArea.isNotEmpty() -> {
                    PuneEvBookingConfirmationScreen(
                        areaName = puneEvBookingArea,
                        bookingDay = puneEvBookingDay,
                        bookingDate = puneEvBookingDate,
                        bookingDateDisplay = puneEvBookingDateDisplay,
                        timeSlot = puneEvBookingTimeSlot,
                        onBackHomeClick = {
                            navController.navigate(Routes.HOME) { popUpTo(Routes.HOME) { inclusive = true } }
                            puneEvBookingArea = ""
                        },
                        onViewBookingsClick = {
                            navController.navigate(Routes.MY_BOOKINGS) { popUpTo(Routes.HOME) }
                            puneEvBookingArea = ""
                        }
                    )
                }
                else -> {
                    // No data found, redirect to home
                    LaunchedEffect(Unit) {
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.HOME) { inclusive = true }
                        }
                    }
                }
            }
        }

        /* ---------------- HYDERABAD PARKING ---------------- */
        composable(Routes.HYDERABAD_LOCATIONS) {
            HyderabadLocationsScreen(
                onBack = { navController.popBackStack() },
                onHitechCityClick = {
                    selectedHyderabadArea = HITECH_CITY
                    navController.navigate(Routes.HYDERABAD_PARKING_DETAILS)
                },
                onBanjaraHillsClick = {
                    selectedHyderabadArea = BANJARA_HILLS
                    navController.navigate(Routes.HYDERABAD_PARKING_DETAILS)
                },
                onJubileeHillsClick = {
                    selectedHyderabadArea = JUBILEE_HILLS
                    navController.navigate(Routes.HYDERABAD_PARKING_DETAILS)
                },
                onGachibowliClick = {
                    selectedHyderabadArea = GACHIBOWLI
                    navController.navigate(Routes.HYDERABAD_PARKING_DETAILS)
                },
                onMadhapurClick = {
                    selectedHyderabadArea = MADHAPUR
                    navController.navigate(Routes.HYDERABAD_PARKING_DETAILS)
                }
            )
        }

        composable(Routes.HYDERABAD_PARKING_DETAILS) {
            selectedHyderabadArea?.let {
                HyderabadParkingDetailsScreen(
                    area = it,
                    onBackClick = { navController.popBackStack() },
                    onBookNowClick = { areaName, day, date, dateDisplay, timeSlot ->
                        hyderabadBookingArea = areaName
                        hyderabadBookingDay = day
                        hyderabadBookingDate = date
                        hyderabadBookingDateDisplay = dateDisplay
                        hyderabadBookingTimeSlot = timeSlot
                        navController.navigate(Routes.BOOKING_CONFIRMATION)
                    }
                )
            }
        }

        /* ---------------- BANGALORE PARKING ---------------- */
        composable(Routes.BANGALORE_LOCATIONS) {
            BangaloreLocationsScreen(
                onBack = { navController.popBackStack() },
                onKoramangalaClick = {
                    selectedBangaloreArea = KORAMANGALA
                    navController.navigate(Routes.BANGALORE_PARKING_DETAILS)
                },
                onIndiranagarClick = {
                    selectedBangaloreArea = INDIRANAGAR
                    navController.navigate(Routes.BANGALORE_PARKING_DETAILS)
                },
                onWhitefieldClick = {
                    selectedBangaloreArea = WHITEFIELD
                    navController.navigate(Routes.BANGALORE_PARKING_DETAILS)
                },
                onElectronicCityClick = {
                    selectedBangaloreArea = ELECTRONIC_CITY
                    navController.navigate(Routes.BANGALORE_PARKING_DETAILS)
                },
                onJayanagarClick = {
                    selectedBangaloreArea = JAYANAGAR
                    navController.navigate(Routes.BANGALORE_PARKING_DETAILS)
                }
            )
        }

        composable(Routes.BANGALORE_PARKING_DETAILS) {
            selectedBangaloreArea?.let {
                BangaloreParkingDetailsScreen(
                    area = it,
                    onBackClick = { navController.popBackStack() },
                    onBookNowClick = { areaName, day, date, dateDisplay, timeSlot ->
                        bangaloreBookingArea = areaName
                        bangaloreBookingDay = day
                        bangaloreBookingDate = date
                        bangaloreBookingDateDisplay = dateDisplay
                        bangaloreBookingTimeSlot = timeSlot
                        navController.navigate(Routes.BOOKING_CONFIRMATION)
                    }
                )
            }
        }

        /* ---------------- MUMBAI PARKING ---------------- */
        composable(Routes.MUMBAI_LOCATIONS) {
            MumbaiLocationsScreen(
                onBack = { navController.popBackStack() },
                onBandraClick = {
                    selectedMumbaiArea = BANDRA
                    navController.navigate(Routes.MUMBAI_PARKING_DETAILS)
                },
                onAndheriClick = {
                    selectedMumbaiArea = ANDHERI
                    navController.navigate(Routes.MUMBAI_PARKING_DETAILS)
                },
                onPowaiClick = {
                    selectedMumbaiArea = POWAI
                    navController.navigate(Routes.MUMBAI_PARKING_DETAILS)
                },
                onWorliClick = {
                    selectedMumbaiArea = WORLI
                    navController.navigate(Routes.MUMBAI_PARKING_DETAILS)
                },
                onColabaClick = {
                    selectedMumbaiArea = COLABA
                    navController.navigate(Routes.MUMBAI_PARKING_DETAILS)
                }
            )
        }

        composable(Routes.MUMBAI_PARKING_DETAILS) {
            selectedMumbaiArea?.let {
                MumbaiParkingDetailsScreen(
                    area = it,
                    onBackClick = { navController.popBackStack() },
                    onBookNowClick = { areaName, day, date, dateDisplay, timeSlot ->
                        mumbaiBookingArea = areaName
                        mumbaiBookingDay = day
                        mumbaiBookingDate = date
                        mumbaiBookingDateDisplay = dateDisplay
                        mumbaiBookingTimeSlot = timeSlot
                        navController.navigate(Routes.BOOKING_CONFIRMATION)
                    }
                )
            }
        }

        /* ---------------- EV CHARGING ---------------- */
        composable(Routes.EV_CHARGING) {
            FindEvChargingScreen(
                onHomeClick = { navController.navigate(Routes.HOME) },
                onProfileClick = { navController.navigate(Routes.PROFILE) },
                onMumbaiEvClick = { navController.navigate(Routes.MUMBAI_EV_CHARGERS) },
                onDelhiEvClick = { navController.navigate(Routes.DELHI_EV_CHARGERS) },
                onBangaloreEvClick = { navController.navigate(Routes.BANGALORE_EV_CHARGERS) },
                onPuneEvClick = { navController.navigate(Routes.PUNE_EV_CHARGERS) },
                onBookingsClick = { navController.navigate(Routes.MY_BOOKINGS) }
            )
        }

        /* ---------------- EV CHARGER DETAILS ---------------- */
        composable(Routes.MUMBAI_EV_CHARGERS) {
            MumbaiEvChargersScreen(
                onBack = { navController.popBackStack() },
                onBkcClick = {
                    selectedMumbaiEvArea = BKC_EV
                    navController.navigate(Routes.MUMBAI_EV_DETAILS)
                },
                onPowaiClick = {
                    selectedMumbaiEvArea = POWAI_EV
                    navController.navigate(Routes.MUMBAI_EV_DETAILS)
                },
                onAndheriClick = {
                    selectedMumbaiEvArea = ANDHERI_EV
                    navController.navigate(Routes.MUMBAI_EV_DETAILS)
                },
                onWorliClick = {
                    selectedMumbaiEvArea = WORLI_EV
                    navController.navigate(Routes.MUMBAI_EV_DETAILS)
                }
            )
        }

        composable(Routes.MUMBAI_EV_DETAILS) {
            selectedMumbaiEvArea?.let {
                MumbaiEvParkingDetailsScreen(
                    area = it,
                    onBackClick = { navController.popBackStack() },
                    onBookNowClick = { areaName, day, date, dateDisplay, timeSlot ->
                        mumbaiEvBookingArea = areaName
                        mumbaiEvBookingDay = day
                        mumbaiEvBookingDate = date
                        mumbaiEvBookingDateDisplay = dateDisplay
                        mumbaiEvBookingTimeSlot = timeSlot
                        navController.navigate(Routes.BOOKING_CONFIRMATION)
                    }
                )
            }
        }

        composable(Routes.DELHI_EV_CHARGERS) {
            DelhiEvChargersScreen(
                onBack = { navController.popBackStack() },
                onCpClick = {
                    selectedDelhiEvArea = CONNAUGHT_PLACE_EV
                    navController.navigate(Routes.DELHI_EV_DETAILS)
                },
                onGurugramClick = {
                    selectedDelhiEvArea = CYBER_HUB_EV
                    navController.navigate(Routes.DELHI_EV_DETAILS)
                },
                onNehruPlaceClick = {
                    selectedDelhiEvArea = NEHRU_PLACE_EV
                    navController.navigate(Routes.DELHI_EV_DETAILS)
                },
                onAerocityClick = {
                    selectedDelhiEvArea = AEROCITY_EV
                    navController.navigate(Routes.DELHI_EV_DETAILS)
                },
                onSaketClick = {
                    selectedDelhiEvArea = SAKET_EV
                    navController.navigate(Routes.DELHI_EV_DETAILS)
                }
            )
        }

        composable(Routes.DELHI_EV_DETAILS) {
            selectedDelhiEvArea?.let {
                DelhiEvParkingDetailsScreen(
                    area = it,
                    onBackClick = { navController.popBackStack() },
                    onBookNowClick = { areaName, day, date, dateDisplay, timeSlot ->
                        delhiEvBookingArea = areaName
                        delhiEvBookingDay = day
                        delhiEvBookingDate = date
                        delhiEvBookingDateDisplay = dateDisplay
                        delhiEvBookingTimeSlot = timeSlot
                        navController.navigate(Routes.BOOKING_CONFIRMATION)
                    }
                )
            }
        }

        composable(Routes.BANGALORE_EV_CHARGERS) {
            BangaloreEvChargersScreen(
                onBack = { navController.popBackStack() },
                onElectronicCityClick = {
                    selectedBangaloreEvArea = ELECTRONIC_CITY_EV
                    navController.navigate(Routes.BANGALORE_EV_DETAILS)
                },
                onWhitefieldClick = {
                    selectedBangaloreEvArea = WHITEFIELD_EV
                    navController.navigate(Routes.BANGALORE_EV_DETAILS)
                },
                onMgRoadClick = {
                    selectedBangaloreEvArea = MG_ROAD_EV
                    navController.navigate(Routes.BANGALORE_EV_DETAILS)
                },
                onManyataClick = {
                    selectedBangaloreEvArea = MANYATA_EV
                    navController.navigate(Routes.BANGALORE_EV_DETAILS)
                },
                onOrionMallClick = {
                    selectedBangaloreEvArea = ORION_MALL_EV
                    navController.navigate(Routes.BANGALORE_EV_DETAILS)
                }
            )
        }

        composable(Routes.BANGALORE_EV_DETAILS) {
            selectedBangaloreEvArea?.let {
                BangaloreEvParkingDetailsScreen(
                    area = it,
                    onBackClick = { navController.popBackStack() },
                    onBookNowClick = { areaName, day, date, dateDisplay, timeSlot ->
                        bangaloreEvBookingArea = areaName
                        bangaloreEvBookingDay = day
                        bangaloreEvBookingDate = date
                        bangaloreEvBookingDateDisplay = dateDisplay
                        bangaloreEvBookingTimeSlot = timeSlot
                        navController.navigate(Routes.BOOKING_CONFIRMATION)
                    }
                )
            }
        }

        composable(Routes.PUNE_EV_CHARGERS) {
            PuneEvChargersScreen(
                onBack = { navController.popBackStack() },
                onHinjewadiClick = {
                    selectedPuneEvArea = HINJEWADI_EV
                    navController.navigate(Routes.PUNE_EV_DETAILS)
                },
                onMagarpattaClick = {
                    selectedPuneEvArea = MAGARPATTA_EV
                    navController.navigate(Routes.PUNE_EV_DETAILS)
                },
                onVimanNagarClick = {
                    selectedPuneEvArea = VIMAN_NAGAR_EV
                    navController.navigate(Routes.PUNE_EV_DETAILS)
                },
                onPimpriChinchwadClick = {
                    selectedPuneEvArea = PIMPRI_CHINCHWAD_EV
                    navController.navigate(Routes.PUNE_EV_DETAILS)
                }
            )
        }

        composable(Routes.PUNE_EV_DETAILS) {
            selectedPuneEvArea?.let {
                PuneEvParkingDetailsScreen(
                    area = it,
                    onBackClick = { navController.popBackStack() },
                    onBookNowClick = { areaName, day, date, dateDisplay, timeSlot ->
                        puneEvBookingArea = areaName
                        puneEvBookingDay = day
                        puneEvBookingDate = date
                        puneEvBookingDateDisplay = dateDisplay
                        puneEvBookingTimeSlot = timeSlot
                        navController.navigate(Routes.BOOKING_CONFIRMATION)
                    }
                )
            }
        }

        /* ---------------- PROFILE ---------------- */
        composable(Routes.PROFILE) {
            ProfileScreen(
                onHomeClick = { navController.navigate(Routes.HOME) },
                onEvClick = { navController.navigate(Routes.EV_CHARGING) },
                onBookingsClick = { navController.navigate(Routes.MY_BOOKINGS) },
                onLogoutClick = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                },
                onEditProfileClick = { navController.navigate(Routes.EDIT_PROFILE) },
                onMembershipClick = { navController.navigate(Routes.MEMBERSHIP_PLANS) },
                onVehiclesClick = { navController.navigate(Routes.MY_VEHICLES) },
                refreshKey = profileRefreshKey
            )
        }

        /* ---------------- EDIT PROFILE ---------------- */
        composable(Routes.EDIT_PROFILE) {
            EditProfileScreen(
                onBackClick = { 
                    profileRefreshKey++
                    navController.popBackStack()
                }
            )
        }

        /* ---------------- MY BOOKINGS ---------------- */
        composable(Routes.MY_BOOKINGS) {
            MyBookingsScreen(
                onBackClick = {
                    // Custom back logic to ensure we don't go back to confirmation
                    if (navController.previousBackStackEntry?.destination?.route == Routes.BOOKING_CONFIRMATION) {
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.HOME) { inclusive = true }
                        }
                    } else {
                        navController.popBackStack()
                    }
                },
                onHomeClick = { navController.navigate(Routes.HOME) },
                onEvClick = { navController.navigate(Routes.EV_CHARGING) },
                onProfileClick = { navController.navigate(Routes.PROFILE) }
            )
        }
    }
}
