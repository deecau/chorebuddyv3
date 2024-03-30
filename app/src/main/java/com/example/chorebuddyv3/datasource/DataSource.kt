package com.example.chorebuddyv3

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow
import java.util.Date

object DataSource {

    data class Member(val id: String, var name: String)
    // Sample initial members data
    //private var _members by mutableStateOf(listOf(Member("Member 1"), Member("Member 2")))
    val members = mutableStateListOf(Member("1", "Member 1"), Member("2", "Member 2"))
//    val members: List<Member>
//        get() = _members

    // Sample chore categories data
    val choreCategories = listOf(
        "Select Chore Type",
        "Cleaning", "Laundry", "Cooking", "Yard Work",
        "Organizing", "Maintenance", "Pet Care", "Miscellaneous"
    )

    // Global variable to store all added chores
    private val _chores = mutableStateOf(mutableListOf<Chore>())

    val chores: List<Chore>
        get() = _chores.value

    // Add a member to the list
//    fun addMember(memberName: String) {
//        _members += Member(memberName)
//    }
//
//    fun removeMember(index: Int) {
//        if (index in members.indices) {
//            _members = _members.toMutableList().also { it.removeAt(index) }
//        }
//    }
    // Add a member to the list
    fun addMember(memberName: String) {
        members.add(Member((members.size + 1).toString(), memberName))
    }

    // Remove a member from the list
    fun removeMember(index: Int) {
        if (index in members.indices) {
            members.removeAt(index)
        }
    }

    // Update a member's name by ID
    fun updateMember(memberId: String, newName: String) {
        val memberIndex = members.indexOfFirst { it.id == memberId }
        if (memberIndex != -1) {
            members[memberIndex] = members[memberIndex].copy(name = newName)
        }
    }

    // Retrieve the list of members
//    fun retrieveMembers(): List<Member> { // Renamed function to retrieveMembers()
//        return _members
//    }

    // Add a chore to the list
    fun addChore(chore: Chore) {
        _chores.value += chore
    }

    // Retrieve the list of chores
    fun retrieveChores(): List<Chore> {
        return _chores.value
    }

    // Sample chore list data
    data class Chore(
        val id: String,
        val category: String,
        val details: String,
        val dueDate: Date,
        val startDate: Date,
        val assignedTo: String,
        val status: String,
        val progress: Float
        //val assignedTo: List<String>
    ) : StateFlow<String?> {
        override val replayCache: List<String?>
            get() = TODO("Not yet implemented")

        override suspend fun collect(collector: FlowCollector<String?>): Nothing {
            TODO("Not yet implemented")
        }

        override val value: String?
            get() = TODO("Not yet implemented")
    }

    // Method to add a chore to the list
    fun addChoreToList(chore: Chore) {
        _chores.value += chore
    }

    // Method to get all chores from the list
    fun getAllChoresFromList(): List<Chore> {
        return _chores.value
    }

    fun getChoreById(choreId: Any): Chore? {
        return chores.find { it.id == choreId }
    }

    // Update chore details
    fun updateChore(choreId: String, category: String, details: String, dueDate: Date, progress: Float) {
        val choreIndex = _chores.value.indexOfFirst { it.id == choreId }
        if (choreIndex != -1) {
            _chores.value[choreIndex] = _chores.value[choreIndex].copy(
                category = category,
                details = details,
                dueDate = dueDate,
                progress = progress
            )
        }
    }
//    fun updateChore(choreId: String, category: String, details: String, dueDate: Date) {
//        val chore = getChoreById(choreId)
//        chore?.let {
//            val updatedChore = it.copy(category = category, details = details, dueDate = dueDate)
//            // Update the chore in the data source
//            // For example:
//            // _chores.value = _chores.value.map { existingChore ->
//            //     if (existingChore.id == choreId) updatedChore else existingChore
//            // }
//        }
//    }


    // Mark chore as done
    fun markChoreAsDone(choreId: String) {
        val choreIndex = _chores.value.indexOfFirst { it.id == choreId }
        if (choreIndex != -1) {
            _chores.value[choreIndex] = _chores.value[choreIndex].copy(status = "Done")
        }
    }


}
