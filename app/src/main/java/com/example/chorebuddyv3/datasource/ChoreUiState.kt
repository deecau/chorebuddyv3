package com.example.chorebuddyv3.datasource

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


class ChoreDetailUiState(initialChoreId: String) {
    var choreId by mutableStateOf(initialChoreId)
        private set

    fun updateChoreId(newChoreId: String) {
        choreId = newChoreId
    }
}

// Chore item data class
data class Chore(val id: String, val task: String)