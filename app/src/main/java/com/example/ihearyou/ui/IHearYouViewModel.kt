package com.example.ihearyou.ui

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale

class IHearYouViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(IHearYouUiState())
    val uiState: StateFlow<IHearYouUiState> = _uiState.asStateFlow()


    // react to the message spoken to the mic
    fun updateText(spokenText: String) {
        _uiState.update { it.copy(isListening = false, lastRecognizedText = spokenText) }
    }

    fun handleRecognitionResult(onUnrecognized: () -> Unit) {
        when (uiState.value.lastRecognizedText) {
            "blue" -> { _uiState.update { it.copy(screenColor = Color.Blue) } }
            "red" -> { _uiState.update { it.copy(screenColor = Color.Red) } }
            "" -> { _uiState.update { it.copy(screenColor = Color.White) }}
            else -> {
                _uiState.update { it.copy(screenColor = Color.White) }
                onUnrecognized()
            }
        }
    }

    fun startListening() {
        _uiState.update { it.copy(isListening = true) }
    }

    fun stopListening() {
        _uiState.update { it.copy(isListening = false) }
    }

    // update permission management state variables
    fun updateShowDialog(show: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(showDialog = show)
        }
    }

    fun updateLaunchAppSettings(launch: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(launchAppSettings = launch)
        }
    }

}