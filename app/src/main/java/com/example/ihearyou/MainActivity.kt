package com.example.ihearyou

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.ihearyou.ui.IHearYouScreen
import com.example.ihearyou.ui.theme.IHearYouTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IHearYouTheme {
                IHearYouScreen()
            }
        }
    }
}
