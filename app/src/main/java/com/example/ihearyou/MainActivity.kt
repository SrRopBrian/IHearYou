package com.example.ihearyou

import android.os.Bundle
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.ihearyou.ui.IHearYouScreen
import com.example.ihearyou.ui.theme.IHearYouTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // ToDo: check if I should push this to IHearYouScreen
        if (!SpeechRecognizer.isRecognitionAvailable(this)) {
            Toast.makeText(
                this,
                R.string.speech_recognizer_unavailable,
                Toast.LENGTH_LONG
            ).show()
        }

        setContent {
            IHearYouTheme {
                IHearYouScreen()
            }
        }
    }
}
