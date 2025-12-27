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
fun BangaloreLocationsScreen(
    onBack: () -> Unit,

    // ✅ NAVIGATION CALLBACKS (LIKE CHENNAI & HYDERABAD)
    onKoramangalaClick: () -> Unit,
    onIndiranagarClick: () -> Unit,
    onWhitefieldClick: () -> Unit,
    onElectronicCityClick: () -> Unit,
    onJayanagarClick: () -> Unit
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
                    text = "Bangalore Locations",
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
                BangaloreLocationItem(
                    title = "Koramangala",
                    subtitle = "Startup Hub",
                    spots = "14 spots",
                    onClick = onKoramangalaClick
                )
            }

            item {
                BangaloreLocationItem(
                    title = "Indiranagar",
                    subtitle = "Shopping District",
                    spots = "9 spots",
                    onClick = onIndiranagarClick
                )
            }

            item {
                BangaloreLocationItem(
                    title = "Whitefield",
                    subtitle = "IT Corridor",
                    spots = "16 spots",
                    onClick = onWhitefieldClick
                )
            }

            item {
                BangaloreLocationItem(
                    title = "Electronic City",
                    subtitle = "Tech Park",
                    spots = "11 spots",
                    onClick = onElectronicCityClick
                )
            }

            item {
                BangaloreLocationItem(
                    title = "Jayanagar",
                    subtitle = "Residential Area",
                    spots = "6 spots",
                    onClick = onJayanagarClick
                )
            }
        }
    }
}

/* ---------- LOCATION ITEM (CLICKABLE) ---------- */

@Composable
fun BangaloreLocationItem(
    title: String,
    subtitle: String,
    spots: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() },   // ✅ CLICK ENABLED
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
                Text(text = title, fontWeight = FontWeight.Bold)
                Text(text = subtitle, fontSize = 12.sp, color = Color.Gray)

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
