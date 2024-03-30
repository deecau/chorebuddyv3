package com.example.chorebuddyv3

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chorebuddyv3.R
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chorebuddyv3.ui.theme.Chorebuddyv3Theme

@Composable
fun IntroPageScreen(navController: NavHostController) {
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
        Box(
            modifier = Modifier
                .weight(6f)
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .background(Color.White),
                verticalArrangement = Arrangement.Top
            ) {
                // Welcome Text
                Text(
                    text = "Welcome to Chores Buddy\n\nFor the 1st time use, please click the profile page to set up your family member 1st.\n\nOnce you have set up your family member, you can start clicking the bottom right hand side button to start adding new Chores.\n\nEnjoy your chores with your Family!",
                    color = Color(0xFF64B7F6),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp),
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = {
                        navController.navigate("MyProfile")
                        // Handle button click action
                        // Navigate or perform any other action here
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "My Profile",
                        fontSize = 16.sp
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
fun IntroPagePreview() {
    Chorebuddyv3Theme {
        val navController = rememberNavController()
        IntroPageScreen(navController = navController)
    }
}

