package com.simats.smartpark

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PuneEvChargersScreen(
    onBack: () -> Unit,
    onHinjewadiClick: () -> Unit,
    onMagarpattaClick: () -> Unit,
    onVimanNagarClick: () -> Unit,
    onPimpriChinchwadClick: () -> Unit
) {

    val currentDate = getPuneDate()   // ✅ UNIQUE FUNCTION

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3FBF8))
            .padding(16.dp)
    ) {

        /* HEADER */
        Text(
            text = "Find EV Charging Stations",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        // 🔴 DATE
        Text(
            text = currentDate,
            fontSize = 12.sp,
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        /* CITY TITLE */
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text("Chargers in Pune", fontWeight = FontWeight.Bold)
        }

        Text(
            text = "4 charging stations",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 48.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        /* PUNE STATIONS */
        PuneChargerCard(
            "Hinjewadi IT Park EV Station",
            "IT & Tech Hub",
            "Fast Charging",
            "₹12/kWh",
            "6 free",
            onClick = onHinjewadiClick
        )

        PuneChargerCard(
            "Magarpatta City Charging",
            "Business & Residential Area",
            "Fast Charging",
            "₹13/kWh",
            "4 free",
            onClick = onMagarpattaClick
        )

        PuneChargerCard(
            "Viman Nagar Mall Charging",
            "Commercial & Shopping Area",
            "Slow Charging",
            "₹9/kWh",
            "5 free",
            onClick = onVimanNagarClick
        )

        PuneChargerCard(
            "Pimpri-Chinchwad MIDC",
            "Industrial Area",
            "Slow Charging",
            "₹8/kWh",
            "7 free",
            onClick = onPimpriChinchwadClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        PuneChargingTipsCard()
    }
}

/* ---------- UNIQUE DATE FUNCTION (NO CONFLICT) ---------- */
fun getPuneDate(): String {
    return SimpleDateFormat(
        "EEE, dd MMM yyyy",
        Locale.getDefault()
    ).format(Date())
}

/* ---------- CHARGER CARD ---------- */
@Composable
fun PuneChargerCard(
    name: String,
    areaType: String,
    type: String,
    price: String,
    free: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(Color(0xFFDFF5E1), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Bolt,
                    contentDescription = null,
                    tint = Color(0xFF2E7D32)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {

                Text(name, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(2.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.LocationCity,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(areaType, fontSize = 12.sp, color = Color.Gray)
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row {
                    Text(type, fontSize = 12.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(price, fontSize = 12.sp, color = Color(0xFF2E7D32))
                }

                Spacer(modifier = Modifier.height(6.dp))
                Text("Available now", fontSize = 11.sp, color = Color.Gray)
            }

            Surface(
                color = Color(0xFFDFF5E1),
                shape = RoundedCornerShape(50)
            ) {
                Text(
                    text = free,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    fontSize = 11.sp,
                    color = Color(0xFF2E7D32)
                )
            }
        }
    }
}

/* ---------- TIPS ---------- */
@Composable
fun PuneChargingTipsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text("Charging Tips", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Pune IT parks and industrial zones experience higher EV usage during weekdays.",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
