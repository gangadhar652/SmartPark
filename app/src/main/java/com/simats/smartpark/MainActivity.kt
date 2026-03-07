package com.simats.smartpark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.simats.smartpark.NavGraph.AppNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // We setContent without passing any savedInstanceState to the NavGraph
        // to ensure it starts from the startDestination every time the activity is created.
        setContent {
            AppNavGraph()
        }
    }
}
