package com.example.chorebuddyv3.ui.theme


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chorebuddyv3.DataSource
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale
import java.util.UUID


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddChore(navController: NavHostController) {
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var choreCategoryText by remember { mutableStateOf("") }
    var enteredText by remember { mutableStateOf("") }
    var memberText by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var selectedMembers by remember { mutableStateOf(listOf<String>()) }
    var enteredDate by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var expandedMenu1 by remember { mutableStateOf(false) }
    var expandedMenu2 by remember { mutableStateOf(false) }
    var selectedMember by remember { mutableStateOf<String?>(null) }

    println("Chore Categories: ${DataSource.choreCategories}")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF003891))
    ) {


        // Top Divider Line
        Spacer(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .background(Color(0xFF1964BD))
        )


        // Body First Row
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            // Column 1: Chore List
            Column(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Chore List",
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 20.dp, end = 10.dp)
                )
            }
            Column(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxHeight()
                    .padding(13.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = choreCategoryText.takeUnless { it.isBlank() } ?: "Select Chore Type",
                        color = Color.Black,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .clickable { expandedMenu1  = true }
                            .padding(8.dp)
                            .background(Color.White)
                            .fillMaxWidth()
                    )
                    DropdownMenu(
                        expanded = expandedMenu1 ,
                        onDismissRequest = { expandedMenu1  = false },
                        modifier = Modifier
                            .background(Color.White)
                            .widthIn(max = 200.dp)
                    ) {
                        DataSource.choreCategories.forEach { choreCategory ->
                            DropdownMenuItem(
                                onClick = {
                                    choreCategoryText = choreCategory
                                    expandedMenu1  = false
                                }
                            ) {
                                Text(
                                    text = choreCategory,
                                    color = Color.Black,
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }

            }
        }



        // Body: Second Row
        Row(
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
        ) {
            // Column 1: Chore Details
            Column(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Chore Details",
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 20.dp, end = 10.dp)
                )
            }

            // Column 2: Text Box
            Column(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxHeight()
                    .padding(13.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                //var enteredText by remember { mutableStateOf("") }
                val textFieldScrollState = rememberScrollState()


                TextField(
                    value = enteredText,
                    onValueChange = {
                        enteredText =  it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(8.dp)
                        .background(Color.White)
                        .verticalScroll(state = textFieldScrollState)
                )
            }
        }

        // Body: Third Row
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            // Column 1: Due Date
            Column(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Due Date",
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 20.dp, end = 10.dp)
                )
            }

            // Column 2: Calendar Box
            Column(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxHeight()
                    .padding(13.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                //var enteredDate by remember { mutableStateOf("") } // Store user-entered date
                var errorMessage by remember { mutableStateOf("") } // Store error message

                // Calendar Box with TextField for manual input
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(200.dp)
                        .background(Color.White)
                ) {
                    TextField(
                        value = enteredDate,
                        onValueChange = { input ->
                            if (input.length <= 10) {
                                enteredDate = input
                                if (input.length == 10) {
                                    val regex = Regex("""^(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])-\d{4}$""")
                                    if (regex.matches(input)) {
                                        val parts = input.split("-")
                                        val month = parts[0].toInt()
                                        val day = parts[1].toInt()
                                        val year = parts[2].toInt()

                                        val isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
                                        val daysInMonth = when (month) {
                                            4, 6, 9, 11 -> 30
                                            2 -> if (isLeapYear) 29 else 28
                                            else -> 31
                                        }

                                        if (day <= daysInMonth) {
                                            errorMessage = "" // 日期有效
                                        } else {
                                            errorMessage = "Invalid day for month"
                                        }
                                    } else {
                                        errorMessage = "Invalid date format"
                                    }
                                } else {
                                    errorMessage = ""
                                }
                            } else {
                                errorMessage = "Invalid date length"
                            }
                        },

                        textStyle = TextStyle(
                            color = if (errorMessage.isNotEmpty()) Color.Red else Color.Black,
                            fontSize = 20.sp
                        ),
                        placeholder = {
                            Text(
                                text = "MM-DD-YYYY",
                                color = Color.Gray,
                                fontSize = 20.sp,
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp)
                    )
                    if (enteredDate.length == 10) {
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            fontSize = 12.sp,
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .align(Alignment.BottomEnd)
                        )
                    }
                }
            }
        }


        // Body: Fourth Row
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            // Column 1: Assignment to Whom
            Column(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Assignment to Whom",
                    color = Color.White,
                    fontSize = 20.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.padding(top = 20.dp, end = 10.dp)
                )
            }
// Column 2: Text Field
            Column(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxHeight()
                    .padding(13.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = selectedMember ?: "Select Member",
                        color = Color.Black,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .clickable { expandedMenu2  = true }
                            .padding(8.dp)
                            .background(Color.White)
                            .fillMaxWidth()
                    )
                    DropdownMenu(
                        expanded = expandedMenu2 ,
                        onDismissRequest = { expandedMenu2  = false },
                        modifier = Modifier
                            .background(Color.White)
                            .widthIn(max = 200.dp)
                    ) {
                        DataSource.members.forEach { member ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedMember = member.name
                                    expandedMenu2  = false
                                }
                            ) {
                                Text(
                                    text = member.name,
                                    color = Color.Black,
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }
            }

        }

        // Body: Final Row
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Column: Button
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        //if (selectedCategory != null && enteredText.isNotBlank() && enteredDate.isNotBlank()) {
//                            val dueDate = Date()
//                            val newChore = DataSource.Chore(
//                                id = UUID.randomUUID().toString(),
//                                category = choreCategoryText,
//                                details = enteredText,
//                                dueDate = dueDate,
//                                assignedTo = memberText
//                                //assignedTo = listOf()
//                            )
//                            DataSource.addChore(newChore)
//
                        if (choreCategoryText.isNotBlank() && enteredText.isNotBlank() && enteredDate.isNotBlank() && selectedMember != null) {
                            val dateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.US)
                            val dueDate = dateFormat.parse(enteredDate)
                            val startDate = dateFormat.parse(LocalDate.now().toString())
                            val newChore = DataSource.Chore(
                                id = UUID.randomUUID().toString(),
                                category = choreCategoryText,
                                details = enteredText,
                                dueDate = dueDate,
                                startDate = startDate,
                                assignedTo = selectedMember!!, // Assign selected member
                                status = "Open",
                                progress = 1F
                            )
                           DataSource.addChore(newChore)
                            navController.navigate("HomePage")
                        }
                        else{
                            System.out.println("Printed "+ choreCategoryText )
                            System.out.println("Printed "+ enteredText )
                            System.out.println("Printed "+ enteredDate )
                        }
                        //}
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .size(width = 200.dp, height = 60.dp)
                ) {
                    Text(
                        text = "Save Chore",
                        fontSize = 20.sp
                    )
                }
            }
        }


        // Bottom Divider Line
        Spacer(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .background(Color(0xFF1964BD))
        )

    }
}


@Preview
@Composable
fun AddChorePreview() {
    Chorebuddyv3Theme {
        val navController = rememberNavController()
        AddChore(navController = navController)
    }
}
