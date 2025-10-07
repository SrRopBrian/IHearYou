package com.example.ihearyou.ui

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.ihearyou.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class IHearYouViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(IHearYouUiState())
    val uiState: StateFlow<IHearYouUiState> = _uiState.asStateFlow()

    fun changeScreenColor() {
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