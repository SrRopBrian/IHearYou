package com.example.ihearyou.ui

import androidx.compose.ui.graphics.Color

data class IHearYouUiState(
    val screenColor: Color = Color.White,
    val showDialog: Boolean = false,
    val launchAppSettings: Boolean = false,
    val isListening: Boolean = false,
    val lastRecognizedText: String? = null
)
