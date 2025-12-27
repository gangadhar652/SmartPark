package com.example.smartpark

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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

/* ---------- DATA MODEL ---------- */

data class Vehicle(
    val name: String,
    val model: String,
    val number: String,
    val type: String,
    val color: String
)

/* ---------- MAIN SCREEN ---------- */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyVehiclesScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Vehicles") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            Text(
                text = "Your Vehicles",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Manage your registered vehicles",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(20.dp))

            /* ---------- VEHICLES LIST ---------- */
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    VehicleCard(
                        vehicle = Vehicle(
                            name = "Honda City",
                            model = "2022",
                            number = "KA 01 AB 1234",
                            type = "Sedan",
                            color = "White"
                        )
                    )
                }

                item {
                    VehicleCard(
                        vehicle = Vehicle(
                            name = "Hyundai Creta",
                            model = "2023",
                            number = "KA 02 CD 5678",
                            type = "SUV",
                            color = "Black"
                        )
                    )
                }
            }
        }
    }
}

/* ---------- VEHICLE CARD ---------- */

@Composable
fun VehicleCard(vehicle: Vehicle) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.DirectionsCar,
                    contentDescription = null,
                    tint = Color(0xFF1976D2),
                    modifier = Modifier.size(32.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = vehicle.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${vehicle.model} • ${vehicle.type}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Divider()

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                VehicleInfo(Icons.Default.ConfirmationNumber, "Number", vehicle.number)
                VehicleInfo(Icons.Default.Palette, "Color", vehicle.color)
            }
        }
    }
}

/* ---------- VEHICLE INFO ---------- */

@Composable
fun VehicleInfo(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = Color.Gray, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(label, fontSize = 11.sp, color = Color.Gray)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(value, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

