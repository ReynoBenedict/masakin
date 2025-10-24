package com.example.masakin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.masakin.ui.navigation.MasakinNavGraph
import com.example.masakin.ui.theme.MasakinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MasakinTheme {
                val navController = rememberNavController()
                MasakinNavGraph(navController = navController)
            }
        }
    }
}
