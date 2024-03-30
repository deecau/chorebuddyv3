package com.example.chorebuddyv3.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChoreDetailViewModel : ViewModel() {
    private val _selectedChoreId = MutableStateFlow<String?>(null)
    val selectedChoreId: StateFlow<String?> = _selectedChoreId.asStateFlow()


    fun setSelectedChoreId(choreId: String) {
        _selectedChoreId.value = choreId
        Log.d("ChoreDetailViewModel", "Selected chore ID set:"+_selectedChoreId.value)
    }


}
