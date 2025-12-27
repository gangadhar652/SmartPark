package com.example.smartpark

import androidx.compose.foundation.background
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

@Composable
fun BangaloreEvChargersScreen(
    onBack: () -> Unit
) {

    val currentDate = getTodayDate() // from DateUtils.kt

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3FBF8))
            .padding(16.dp)
    ) {

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

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text("Chargers in Bangalore", fontWeight = FontWeight.Bold)
        }

        Text(
            text = "5 charging stations",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 48.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        BangaloreChargerCard(
            "Electronic City EV Station",
            "IT & Tech Hub",
            "Fast Charging",
            "₹12/kWh",
            "8 free"
        )

        BangaloreChargerCard(
            "Whitefield Tech Park",
            "IT & Corporate Area",
            "Fast Charging",
            "₹13/kWh",
            "6 free"
        )

        BangaloreChargerCard(
            "MG Road Metro Charging",
            "Commercial & Transit Zone",
            "Fast Charging",
            "₹14/kWh",
            "5 free"
        )

        BangaloreChargerCard(
            "Manyata Tech Park",
            "Business & IT Park",
            "Slow Charging",
            "₹9/kWh",
            "7 free"
        )

        BangaloreChargerCard(
            "Orion Mall Rajajinagar",
            "Shopping & Commercial Area",
            "Slow Charging",
            "₹10/kWh",
            "4 free"
        )

        Spacer(modifier = Modifier.height(24.dp))

        BangaloreChargingTipsCard()
    }
}

/* ---------------- CARD ---------------- */

@Composable
fun BangaloreChargerCard(
    name: String,
    areaType: String,
    type: String,
    price: String,
    free: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
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
                Icon(Icons.Default.Bolt, contentDescription = null, tint = Color(0xFF2E7D32))
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

/* ---------------- TIPS ---------------- */

@Composable
fun BangaloreChargingTipsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Charging Tips", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "IT hubs in Bangalore experience peak EV charging demand during office hours.",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
