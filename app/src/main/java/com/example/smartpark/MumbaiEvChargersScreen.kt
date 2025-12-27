package com.example.smartpark

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
fun MumbaiEvChargersScreen(
    onBack: () -> Unit,

    // ✅ NAVIGATION CALLBACKS
    onBkcClick: () -> Unit,
    onPowaiClick: () -> Unit,
    onAndheriClick: () -> Unit,
    onWorliClick: () -> Unit
) {

    val currentDate = SimpleDateFormat(
        "EEE, dd MMM yyyy",
        Locale.getDefault()
    ).format(Date())

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
            Text("Chargers in Mumbai", fontWeight = FontWeight.Bold)
        }

        Text(
            text = "4 charging stations",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 48.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        /* MUMBAI STATIONS */
        MumbaiChargerCard(
            name = "Bandra Kurla Complex",
            areaType = "IT Hub",
            type = "Fast Charging",
            price = "₹14/kWh",
            free = "8 free",
            onClick = onBkcClick
        )

        MumbaiChargerCard(
            name = "Powai Lake Charging",
            areaType = "Commercial Area",
            type = "Fast Charging",
            price = "₹13/kWh",
            free = "4 free",
            onClick = onPowaiClick
        )

        MumbaiChargerCard(
            name = "Andheri Metro",
            areaType = "Transit Zone",
            type = "Slow Charging",
            price = "₹9/kWh",
            free = "6 free",
            onClick = onAndheriClick
        )

        MumbaiChargerCard(
            name = "Worli Sea Link Plaza",
            areaType = "Business District",
            type = "Fast Charging",
            price = "₹15/kWh",
            free = "2 free",
            onClick = onWorliClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        MumbaiChargingTipsCard()
    }
}

/* ---------- CHARGER CARD ---------- */
@Composable
fun MumbaiChargerCard(
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
            .clickable { onClick() },   // ✅ NAVIGATION HERE
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
fun MumbaiChargingTipsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text("Charging Tips", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Mumbai charging stations are busiest during office hours and weekends.",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
