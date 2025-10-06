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

class IHearYouViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(IHearYouUiState())
    val uiState: StateFlow<IHearYouUiState> = _uiState.asStateFlow()

    private var screenColor = uiState.value.screenColor

    fun changeScreenColor(): Color {
        when (screenColor) {
            Color.White -> screenColor = Color.Blue
            Color.Blue -> screenColor = Color.Red
            Color.Red -> screenColor = Color.Blue
        }

        return screenColor
    }
}