package com.example.smartpark

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HyderabadLocationsScreen(
    onBack: () -> Unit,

    // ✅ NAVIGATION CALLBACK (SAME STYLE AS CHENNAI)
    onHitechCityClick: () -> Unit,
    onBanjaraHillsClick: () -> Unit,
    onJubileeHillsClick: () -> Unit,
    onGachibowliClick: () -> Unit,
    onMadhapurClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        /* HEADER */
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Back"
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = "Hyderabad Locations",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "5 areas available",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        /* LOCATION LIST */
        LazyColumn {

            item {
                HyderabadLocationItem(
                    title = "Hitech City",
                    subtitle = "IT Corridor",
                    spots = "18 spots",
                    onClick = onHitechCityClick
                )
            }

            item {
                HyderabadLocationItem(
                    title = "Banjara Hills",
                    subtitle = "Upscale Locality",
                    spots = "7 spots",
                    onClick = onBanjaraHillsClick
                )
            }

            item {
                HyderabadLocationItem(
                    title = "Jubilee Hills",
                    subtitle = "Premium Area",
                    spots = "5 spots",
                    onClick = onJubileeHillsClick
                )
            }

            item {
                HyderabadLocationItem(
                    title = "Gachibowli",
                    subtitle = "Tech Hub",
                    spots = "10 spots",
                    onClick = onGachibowliClick
                )
            }

            item {
                HyderabadLocationItem(
                    title = "Madhapur",
                    subtitle = "Commercial Zone",
                    spots = "8 spots",
                    onClick = onMadhapurClick
                )
            }
        }
    }
}

/* ---------- LOCATION ITEM (CLICKABLE) ---------- */

@Composable
fun HyderabadLocationItem(
    title: String,
    subtitle: String,
    spots: String,
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
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFE3F2FD), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color(0xFF1976D2)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold)
                Text(subtitle, fontSize = 12.sp, color = Color.Gray)

                Spacer(modifier = Modifier.height(4.dp))

                Surface(
                    color = Color(0xFFC8E6C9),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = spots,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
                        fontSize = 11.sp,
                        color = Color(0xFF2E7D32)
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null
            )
        }
    }
}
