package com.example.chorebuddyv3.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chorebuddyv3.DataSource
import com.example.chorebuddyv3.ui.ChoreDetailViewModel

@Composable
fun History(navController: NavHostController) {
    val choreDetailViewModel: ChoreDetailViewModel = viewModel()
    val doneChores = DataSource.retrieveChores().filter { it.status == "Done" } // Fetch done chores from DataSource
    var selectedChoreId by remember(choreDetailViewModel.selectedChoreId) { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Display the chore items
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(doneChores) { chore ->
                // Display each chore item
                DoneChoreItem(chore = chore) {
                    choreDetailViewModel.setSelectedChoreId(chore.id)
                    selectedChoreId = choreDetailViewModel.selectedChoreId.value
//                    if (choreDetailViewModel.selectedChoreId.value != null) {
//                        // The selected chore ID is set, do something
//                        println("Selected chore ID: ${choreDetailViewModel.selectedChoreId.value}")
//                    } else {
//                        // The selected chore ID is not set
//                        println("Selected chore ID is null")
//                    }
                    navController.navigate(route = "ChoreDetail/$selectedChoreId")
                }
            }
        }

        // Button to navigate to AddChore screen
        Button(
            onClick = {
                navController.navigate("AddChore")
                // Handle button click action
                // Navigate or perform any other action here
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Add New Chore",
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun DoneChoreItem(chore: DataSource.Chore, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), // Make the whole card clickable
        backgroundColor = Color(0xFF003891) // Set the background color to green
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "TaskID: ${chore.id}",
                style = TextStyle(color = Color.White, fontSize = 16.sp)
            )
            Text(
                text = "Category: ${chore.category}",
                style = TextStyle(color = Color.White, fontSize = 16.sp)
            )
            Text(
                text = "Details: ${chore.details}",
                style = TextStyle(color = Color.White, fontSize = 16.sp)
            )
            Text(
                text = "Due Date: ${chore.dueDate}",
                style = TextStyle(color = Color.White, fontSize = 16.sp)
            )
        }
    }
}

//@Preview
//@Composable
//fun HomePagePreview() {
//    Chorebuddyv3Theme {
//        val navController = rememberNavController()
//        History(navController = navController)
//    }
//}