package com.example.visilitsa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.visilitsa.ui.screens.VisilitsaScreen
import com.example.visilitsa.ui.theme.VisilitsaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VisilitsaTheme {
                VisilitsaScreen()
            }
        }
    }
}