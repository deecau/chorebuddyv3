package com.example.chorebuddyv3.ui

// ChoreDetail.kt
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chorebuddyv3.DataSource
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import java.util.Calendar


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ChoreDetail(
    navController: NavHostController,
    choreId: String?,
    onUpdateChore: (String, String, String, Date, Float) -> Unit,
    onMarkAsDone: (String) -> Unit
) {
    val chore = choreId?.let { DataSource.getChoreById(it) }
    var dueDateTextFieldValue by remember { mutableStateOf(TextFieldValue()) }
    var category by remember { mutableStateOf(chore?.category ?: "") }
    var details by remember { mutableStateOf(chore?.details ?: "") }
    var dueDate by remember { mutableStateOf(chore?.dueDate ?: Date()) }
    var progress by remember { mutableStateOf(1) }
    var errorMessage by remember { mutableStateOf("") }
    val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    val formattedDate = remember(dueDate) { dateFormat.format(dueDate) }

    Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
        Text(text = "TaskID: ${chore?.id}")
        Column(modifier = Modifier) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(60.dp)
                    .background(Color.White)
            ) {
                TextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category") }
                )
            }
        }
        Column(modifier = Modifier.padding(vertical = 2.dp)) {

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .height(60.dp)
                    .background(Color.White)
            ) {
                TextField(
                    value = details,
                    onValueChange = { details = it },
                    label = { Text("Details") }
                )
            }
        }
        // Calendar input for due date

                // TextField for calendar input
                TextField(
                    value = dueDateTextFieldValue,
                    onValueChange = {
                        // Your existing logic for updating the due date based on input
                        dueDateTextFieldValue = it
                    },
                    textStyle = TextStyle(
                        color = if (errorMessage.isNotEmpty()) Color.Red else Color.Black,
                        fontSize = 20.sp
                    ),
                    label = { Text("Due Date") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            // Handle done action if needed
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp)
                )

                // Display error message if any
                if (dueDateTextFieldValue.text.length == 10) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 12.sp,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .padding(end = 8.dp)

                    )
                }


        // Radio group for progress selection
        Text("Progress")
        (1..5).forEach { value ->
            val description = when (value) {
                1 -> "Getting Started"
                2 -> "On Track"
                3 -> "Making Progress"
                4 -> "Nearly Completed"
                5 -> "Completed"
                else -> ""
            }
            RadioOption(
                text = value.toString(),
                description = description,
                selected = progress == value,
                onSelect = { progress = value }
            )
        }


        Button(onClick = {
            onUpdateChore(choreId ?: "", category, details, dueDate, progress.toFloat())
            navController.navigate("HomePage")
            }
        ) {
            Text(text = "Update Chore")

        }

        Button(onClick = {
            onMarkAsDone(choreId ?: "")
            navController.navigate("HomePage")
        }) {
            Text(text = "Mark as Done")
        }

        // Example button to navigate back
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Back")
        }
    }
}

@Composable
fun RadioOption(text: String, description: String, selected: Boolean, onSelect: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        androidx.compose.material.RadioButton(
            selected = selected,
            onClick = onSelect
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = text)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = description, style = MaterialTheme.typography.body2)
            }
        }
    }
}



fun getChoreTask(choreId: String): String {
    // Dummy implementation to fetch task details
    return "Task for Chore ID: $choreId"
}