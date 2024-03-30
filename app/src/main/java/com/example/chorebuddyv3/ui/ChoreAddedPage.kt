package com.example.chorebuddyv3.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

// Define the parameters that you expect to pass to ChoreAddedPage
@Composable
fun ChoreAddedPage(navController: NavHostController, category: String, details: String, dueDate: String) {
//fun ChoreAddedPage(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Use the passed parameters to display information about the added chore
        Text(
            text = "A $category Chore was successfully added\nDetails: $details\nDue Date is $dueDate",
            //text = "A category Chore was successfully added\nDetails: details\nDue Date is DueDate",
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}
