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

    fun changeScreenColor(): Int {
        when (screenColor) {
            R.string.white -> screenColor = R.string.blue
            R.string.blue -> screenColor = R.string.red
            R.string.red -> screenColor = R.string.blue
        }

        return screenColor
    }

    fun getColor(context: Context): Color {
        return when (screenColor) {
            R.string.blue -> Color.Blue
            R.string.red -> Color.Red
            else -> Color.White
        }
    }

}