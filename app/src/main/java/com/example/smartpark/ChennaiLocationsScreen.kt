package com.example.smartpark

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChennaiLocationsScreen(
    onBack: () -> Unit,

    // ✅ NAVIGATION CALLBACKS FOR ALL AREAS
    onTNagarClick: () -> Unit,
    onAnnaNagarClick: () -> Unit,
    onVelacheryClick: () -> Unit,
    onAdyarClick: () -> Unit,
    onMylaporeClick: () -> Unit
) {

    Column(modifier = Modifier.padding(16.dp)) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
            Spacer(Modifier.width(8.dp))
            Column {
                Text("Chennai Locations", fontWeight = FontWeight.Bold)
                Text("5 areas available", fontSize = 12.sp, color = Color.Gray)
            }
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {

            item {
                LocationItem(
                    title = "T Nagar",
                    subtitle = "Shopping District",
                    spots = "12 spots",
                    onClick = onTNagarClick
                )
            }

            item {
                LocationItem(
                    title = "Anna Nagar",
                    subtitle = "Residential Area",
                    spots = "8 spots",
                    onClick = onAnnaNagarClick
                )
            }

            item {
                LocationItem(
                    title = "Velachery",
                    subtitle = "IT Hub",
                    spots = "15 spots",
                    onClick = onVelacheryClick
                )
            }

            item {
                LocationItem(
                    title = "Adyar",
                    subtitle = "Coastal Area",
                    spots = "6 spots",
                    onClick = onAdyarClick
                )
            }

            item {
                LocationItem(
                    title = "Mylapore",
                    subtitle = "Cultural Center",
                    spots = "4 spots",
                    onClick = onMylaporeClick
                )
            }
        }
    }
}

/* ---------- LOCATION ITEM ---------- */

@Composable
fun LocationItem(
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
                    Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color.Blue
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold)
                Text(subtitle, fontSize = 12.sp, color = Color.Gray)

                Spacer(Modifier.height(4.dp))

                Surface(
                    color = Color(0xFFC8E6C9),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        spots,
                        modifier = Modifier.padding(
                            horizontal = 10.dp,
                            vertical = 2.dp
                        ),
                        fontSize = 11.sp,
                        color = Color(0xFF2E7D32)
                    )
                }
            }

            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
        }
    }
}
