package com.example.smartpark.NavGraph

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartpark.*

/* ---------------- ROUTES ---------------- */

object Routes {

    const val SPLASH = "splash"

    const val ONBOARDING_1 = "onboarding_1"
    const val ONBOARDING_2 = "onboarding_2"
    const val ONBOARDING_3 = "onboarding_3"

    const val PERMISSIONS = "permissions"
    const val LOGIN = "login"
    const val SIGNUP = "signup"

    const val HOME = "home"
    const val EV_CHARGING = "ev_charging"
    const val PROFILE = "profile"

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
    const val CHENNAI_BOOKING_CONFIRMATION = "chennai_booking_confirmation"

    // EV Charging
    const val MUMBAI_EV_CHARGERS = "mumbai_ev_chargers"
    const val DELHI_EV_CHARGERS = "delhi_ev_chargers"
    const val BANGALORE_EV_CHARGERS = "bangalore_ev_chargers"
    const val PUNE_EV_CHARGERS = "pune_ev_chargers"
}

/* ---------------- NAV GRAPH ---------------- */

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()

    // Selected parking holders
    var selectedHyderabadArea by remember { mutableStateOf<HyderabadParkingArea?>(null) }
    var selectedBangaloreArea by remember { mutableStateOf<BangaloreParkingArea?>(null) }
    var selectedMumbaiArea by remember { mutableStateOf<MumbaiParkingArea?>(null) }

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {

        /* ---------------- SPLASH ---------------- */
        composable(Routes.SPLASH) {
            SplashScreen {
                navController.navigate(Routes.ONBOARDING_1) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            }
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
                navController.navigate(Routes.PERMISSIONS)
            }
        }

        /* ---------------- PERMISSIONS ---------------- */
        composable(Routes.PERMISSIONS) {
            EnablePermissionsScreen(
                onContinueClick = { navController.navigate(Routes.LOGIN) },
                onSkipClick = { navController.navigate(Routes.LOGIN) }
            )
        }

        /* ---------------- LOGIN ---------------- */
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginClick = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onForgotPasswordClick = {},
                onGoogleLoginClick = {},
                onSignUpClick = { navController.navigate(Routes.SIGNUP) }
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
                onProfileClick = { navController.navigate(Routes.PROFILE) }
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
                onBookNowClick = {
                    navController.navigate(Routes.CHENNAI_BOOKING_CONFIRMATION)
                }
            )
        }

        /* ---------------- CHENNAI BOOKING CONFIRMATION ---------------- */
        composable(Routes.CHENNAI_BOOKING_CONFIRMATION) {
            ChennaiBookingConfirmationScreen(
                onBackHomeClick = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                }
            )
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
                    onBookNowClick = {}
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
                    onBookNowClick = {}
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
                    onBookNowClick = {}
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
                onPuneEvClick = { navController.navigate(Routes.PUNE_EV_CHARGERS) }
            )
        }

        composable(Routes.MUMBAI_EV_CHARGERS) {
            MumbaiEvChargersScreen(
                onBack = { navController.popBackStack() },
                onBkcClick = {},
                onPowaiClick = {},
                onAndheriClick = {},
                onWorliClick = {}
            )
        }

        composable(Routes.DELHI_EV_CHARGERS) {
            DelhiEvChargersScreen { navController.popBackStack() }
        }

        composable(Routes.BANGALORE_EV_CHARGERS) {
            BangaloreEvChargersScreen { navController.popBackStack() }
        }

        composable(Routes.PUNE_EV_CHARGERS) {
            PuneEvChargersScreen { navController.popBackStack() }
        }

        /* ---------------- PROFILE ---------------- */
        composable(Routes.PROFILE) {
            ProfileScreen(
                onHomeClick = { navController.navigate(Routes.HOME) },
                onEvClick = { navController.navigate(Routes.EV_CHARGING) },
                onLogoutClick = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                }
            )
        }
    }
}
