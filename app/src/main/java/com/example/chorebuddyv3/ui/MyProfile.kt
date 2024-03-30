package com.example.chorebuddyv3.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chorebuddyv3.DataSource


@Composable
fun MyProfile(navController: NavHostController) {
    val (newMemberName, setNewMemberName) = remember { mutableStateOf("") }
    //val (members, setMembers) = remember { mutableStateOf(listOf("Member 1", "Member 2")) }
    //val members = viewModel.members.value
    //val members by remember { viewModel.members }
    //val members by remember { DataSource.retrieveMembers() }
    val members by remember { mutableStateOf(DataSource.members) }
    val (isDialogOpen, setIsDialogOpen) = remember { mutableStateOf(false) }
    val (isDeleteDialogOpen, setIsDeleteDialogOpen) = remember { mutableStateOf(false) }
    val (memberToDeleteIndex, setMemberToDeleteIndex) = remember { mutableStateOf<Int?>(null) }
    val (isEditDialogOpen, setIsEditDialogOpen) = remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(members) {
        // No-op, just to trigger recomposition when members list changes
    }

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

        // Body
        Row(
            modifier = Modifier
                .weight(5f)
                .fillMaxWidth()
        ) {
            // Row 1, Column 1
            Column(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Test text
                Text(
                    text = "Members",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp)
                )
            }

            Column(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {


                // Show added members
                members.forEachIndexed { index, member ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        // Member's name
                        Text(
                            text = member.name,
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(4.dp)
                        )

                        // Delete button
                        Button(
                            onClick = {
                                setIsDeleteDialogOpen(true)
                                setMemberToDeleteIndex(index) // Set the index of the member to delete
                            },
                            modifier = Modifier
                                .padding(4.dp, 10.dp)
                                .height(30.dp)
                                .width(90.dp),
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Text(
                                text = "Delete",
                                color = Color.White,
                                fontSize = 12.sp,
                            )
                        }



                        // Delete Member Dialog
                        if (isDeleteDialogOpen && index == memberToDeleteIndex) {
                            AlertDialog(
                                onDismissRequest = { setIsDeleteDialogOpen(false) },
                                title = { Text("Delete Member") },
                                text = {
                                    Text("Delete this member?")
                                },
                                confirmButton = {
                                    Button(
                                        onClick = {
                                            // Perform delete operation here using memberToDeleteIndex
                                            memberToDeleteIndex?.let { index ->
                                                if (index >= 0 && index < members.size) {
                                                    DataSource.removeMember(index)
                                                    setIsDeleteDialogOpen(false)
                                                }
                                            }
                                        },
                                        modifier = Modifier.padding(8.dp)
                                    ) {
                                        Text(text = "Confirm")
                                    }
                                },
                                dismissButton = {
                                    Button(
                                        onClick = { setIsDeleteDialogOpen(false) },
                                        modifier = Modifier.padding(8.dp)
                                    ) {
                                        Text(text = "Cancel")
                                    }
                                }
                            )
                        }
                    }
                }

                Button(
                    onClick = { setIsDialogOpen(true) },
                    modifier = Modifier
                        .padding(2.dp, 10.dp)
                        .height(40.dp)
                        .width(150.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Add Member",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(2.dp)
                    )
                }
            }
        }


// Add Member Dialog
        if (isDialogOpen) {
            var showErrorMessage by remember { mutableStateOf(false) } // Add a state variable to track when to show the error message
            AlertDialog(
                onDismissRequest = { setIsDialogOpen(false) },
                title = { Text("Add Member") },
                text = {
                    Column {
                        TextField(
                            value = newMemberName,
                            onValueChange = {
                                setNewMemberName(it)
                                // Reset the error message when the user types in the TextField
                                showErrorMessage = false
                            },
                            label = { Text("Enter member's name") },
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 1 // Set maxLines to 1 to ensure single line input
                        )
                        // Show the error message only if the user tries to save a null member
                        if (showErrorMessage && newMemberName.isBlank()) {
                            Text(
                                text = "Please enter a name",
                                color = Color.Red,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (newMemberName.isNotBlank()) {
                                DataSource.addMember(newMemberName)
                                //setMembers(listOf(newMemberName) + members)
                                setNewMemberName("")
                                setIsDialogOpen(false)
                            } else {
                                // Set the flag to show the error message when the user tries to save a null member
                                showErrorMessage = true
                            }
                        },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = "Save")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { setIsDialogOpen(false) },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = "Cancel")
                    }
                }
            )
        }


        // Row 2
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Inside the Row where the "Edit" button is located
                Button(
                    onClick = {
                        setIsEditDialogOpen(true)},
                    modifier = Modifier
                        .padding(20.dp, 10.dp)
                        .height(40.dp)
                        .width(100.dp),
                    shape = RoundedCornerShape(10.dp),
                    //colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1EF973))
                ) {
                    Text(
                        text = "Edit",
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(0.dp)
                    )
                }

                // An AlertDialog for edit dialog
                if (isEditDialogOpen) {
                    AlertDialog(
                        onDismissRequest = { setIsEditDialogOpen(false) },
                        title = { Text("Edit Members") },
                        text = {
                            Column {
                                members.forEachIndexed { index, member ->
                                    // Define a TextField for each member's name
                                    var editedMemberName by remember { mutableStateOf(member.name) }

                                    TextField(
                                        value = editedMemberName,
                                        onValueChange = {newName ->
                                            editedMemberName = newName
                                            DataSource.updateMember(member.id, newName)
                                        },
                                        label = { Text("Enter member's name") },
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    // Close the edit dialog
                                    setIsEditDialogOpen(false)
                                },
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Text(text = "Save")
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = { setIsEditDialogOpen(false) },
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Text(text = "Cancel")
                            }
                        }
                    )

                }



                Button(
                    onClick = {
                        navController.navigate("AddChore")//DataPersistence.saveMemberList(context, members)
                    },
                    modifier = Modifier
                        .padding(20.dp, 10.dp)
                        .height(40.dp)
                        .width(150.dp),
                    shape = RoundedCornerShape(10.dp),
                    //colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1EF973))
                ) {
                    Text(
                        text = "Add Chore",
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(0.dp)
                    )
                }
            }

            // Bottom Divider Line




        }
    }
}



@Preview
@Composable
fun MyProfilePreview() {
    Chorebuddyv3Theme {
        val navController = rememberNavController()
        MyProfile(navController = navController)
    }
}
