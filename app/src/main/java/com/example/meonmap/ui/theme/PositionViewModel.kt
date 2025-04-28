package com.example.meonmap.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class PositionViewModel : ViewModel() {
    var position by mutableStateOf<PositionData?>(null)
        private set

    fun updatePosition(newPosition: PositionData) {
        position = newPosition
    }
}