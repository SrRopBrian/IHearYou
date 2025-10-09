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

    fun updateScreenColor() {
        _uiState.update { currentState ->
            val newColor = when (currentState.screenColor) {
                Color.White -> Color.Blue
                Color.Blue -> Color.Red
                Color.Red -> Color.Blue
                else -> Color.White
            }
            currentState.copy(screenColor = newColor)
        }

    }

    fun handleRecognitionResult(result: String) {
        _uiState.update { it.copy(isListening = false, lastRecognizedText = result) }
        when (result.lowercase(Locale.getDefault())) {
            "blue" -> { _uiState.update { it.copy(screenColor = Color.Blue) } }
            "red" -> { _uiState.update { it.copy(screenColor = Color.Red) } }
            else -> { // ToDo -  add a message that the color wasn't recognized
                _uiState.update { it.copy(screenColor = Color.White) }
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