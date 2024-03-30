package com.example.chorebuddyv3.ui.theme
import android.os.Build
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chorebuddyv3.DataSource
import com.example.chorebuddyv3.ui.ChoreDetailViewModel
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomePage(navController: NavHostController) {
    val choreDetailViewModel: ChoreDetailViewModel = viewModel()
    val chores = DataSource.retrieveChores().filter { it.status == "Open" } // Fetch chores from DataSource
    var selectedChoreId by remember(choreDetailViewModel.selectedChoreId) { mutableStateOf<String?>(null) }

    // Custom date formatter
    val dateFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFF003891))
    ) {
        // Display the chore items
        Box(
            modifier = Modifier.weight(1f)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                // Sort chores by descending order of due date
                val sortedChores = chores.sortedBy { LocalDate.parse(it.dueDate.toString(), dateFormatter) }
                items(sortedChores) { chore ->
                    // Display each chore item
                    ChoreItem(chore = chore) {
                        choreDetailViewModel.setSelectedChoreId(chore.id)
                        selectedChoreId = choreDetailViewModel.selectedChoreId.value
                        if (choreDetailViewModel.selectedChoreId.value != null) {
                            // The selected chore ID is set, do something
                            println("Selected chore ID: ${choreDetailViewModel.selectedChoreId.value}")
                        } else {
                            // The selected chore ID is not set
                            println("Selected chore ID is null")
                        }
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
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Add New Chore",
                    fontSize = 16.sp
                )
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChoreItem(chore: DataSource.Chore, onClick: () -> Unit) {
    val currentDate = LocalDate.now()
    val dateFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
    val dueDate = LocalDate.parse(chore.dueDate.toString(), dateFormatter)

    // Determine the progress bar color based on due date
    val progressBarColor = if (dueDate.isBefore(currentDate)) {
        Color.Red // Change to red if due date is overdue
    } else {
        MaterialTheme.colors.primary // Use primary color as default
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() } // Make the whole card clickable
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            //Text(text = "TaskID: ${chore.id}")
            Text(text = "Assigned: ${chore.assignedTo}")
            Text(text = "Due Date: ${chore.dueDate}")
            Text(text = "Category: ${chore.category}")
            Text(text = "Details: ${chore.details}")

            // Pass the progress bar color to the ProgressBar composable
            ProgressBar(chore.progress, progressBarColor)
        }
    }
}

@Composable
fun ProgressBar(progress: Float, progressBarColor: Color) {
    // LinearProgressIndicator from Material Components
    LinearProgressIndicator(
        progress = progress / 5, // Scale the progress value to fit between 0 and 1
        color = progressBarColor, // Set the color dynamically based on due date
        modifier = Modifier.fillMaxWidth()
    )
}





//@Preview
//@Composable
//fun HomePagePreview() {
//    // Assuming you have Chorebuddyv3Theme defined
//    val navController = rememberNavController()
//    HomePage(navController = navController)
//}
