package com.example.chorebuddyv3.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MyProfileViewModel : ViewModel() {
    val members = mutableStateOf(listOf<String>())

    fun addMember(member: String) {
        members.value += member
    }

    fun removeMember(index: Int) {
        val updatedMembers = members.value.toMutableList()
        updatedMembers.removeAt(index)
        members.value = updatedMembers
    }
}