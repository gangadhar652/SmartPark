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
fun MumbaiLocationsScreen(
    onBack: () -> Unit,

    // ✅ NAVIGATION CALLBACKS
    onBandraClick: () -> Unit,
    onAndheriClick: () -> Unit,
    onPowaiClick: () -> Unit,
    onWorliClick: () -> Unit,
    onColabaClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        /* HEADER */
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Back")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text("Mumbai Locations", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("5 areas available", fontSize = 12.sp, color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        /* LOCATION LIST */
        LazyColumn {

            item {
                MumbaiLocationItem(
                    title = "Bandra",
                    subtitle = "Entertainment Hub",
                    spots = "20 spots",
                    onClick = onBandraClick
                )
            }

            item {
                MumbaiLocationItem(
                    title = "Andheri",
                    subtitle = "Commercial Center",
                    spots = "15 spots",
                    onClick = onAndheriClick
                )
            }

            item {
                MumbaiLocationItem(
                    title = "Powai",
                    subtitle = "IT Park",
                    spots = "12 spots",
                    onClick = onPowaiClick
                )
            }

            item {
                MumbaiLocationItem(
                    title = "Worli",
                    subtitle = "Business District",
                    spots = "10 spots",
                    onClick = onWorliClick
                )
            }

            item {
                MumbaiLocationItem(
                    title = "Colaba",
                    subtitle = "Tourist Area",
                    spots = "8 spots",
                    onClick = onColabaClick
                )
            }
        }
    }
}

/* ---------- LOCATION CARD (CLICKABLE) ---------- */

@Composable
fun MumbaiLocationItem(
    title: String,
    subtitle: String,
    spots: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() },   // ✅ ENABLE CLICK
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
                    Icons.Default.LocationOn,
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
                Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.LightGray
            )
        }
    }
}
