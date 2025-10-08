package com.example.ihearyou.ui

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class IHearYouViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(IHearYouUiState())
    val uiState: StateFlow<IHearYouUiState> = _uiState.asStateFlow()

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
}