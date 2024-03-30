package com.example.chorebuddyv3

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.chorebuddyv3.ui.theme.Chorebuddyv3Theme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.dp
import com.example.chorebuddyv3.ui.theme.AddChore
import com.example.chorebuddyv3.ui.theme.HomePage
import com.example.chorebuddyv3.ui.theme.MyProfile
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.chorebuddyv3.ui.ChoreDetail
import com.example.chorebuddyv3.ui.ChoreDetailViewModel
import com.example.chorebuddyv3.ui.History

enum class choreBuddyScreen(@StringRes val title: Int) {
    Home(title = R.string.app_name),
    //ChoreDetail(title = R.string.chore_detail),
    AddChore(title = R.string.new_chore),
    History(title = R.string.history),
    MyProfile(title = R.string.profile),
    IntroPageScreen(title = R.string.intro_screen),
    HomePage(title = R.string.home),
    ChoreAddedPage(title = R.string.added_chore),
    ChoreDetail(title = R.string.chore_detail)

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun choreBuddyAppBar(
    currentScreen: choreBuddyScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.image_size))
                        .padding(dimensionResource(R.dimen.padding_small)),
                    painter = painterResource(R.drawable.chores_buddy),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.weight(0.5f))
                Text(
                    text = stringResource(currentScreen.title),
                    style = MaterialTheme.typography.displayMedium.copy(color = Color.White)

                )
                Spacer(modifier = Modifier.weight(1f))
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color(0xFF003891)
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun MyBottomNavigationBar(
    selectedTab: choreBuddyScreen,
    onTabSelected: (choreBuddyScreen) -> Unit,
    navController: NavHostController
) {
    BottomNavigation(
        modifier = Modifier.fillMaxWidth(),
        elevation = 8.dp,
        backgroundColor = Color(0xFF003891)
    ) {
        BottomNavigationItem(
            selected = selectedTab == choreBuddyScreen.HomePage,
            onClick = { onTabSelected(choreBuddyScreen.HomePage) },
            icon = {
                Icon(Icons.Default.Home, contentDescription = null, tint = Color.White)
            },
            label = { Text(stringResource(id = R.string.home),style = TextStyle(color = Color.White)) }
        )
        BottomNavigationItem(
            selected = selectedTab == choreBuddyScreen.MyProfile,
            onClick = { onTabSelected(choreBuddyScreen.MyProfile) },
            icon = {
                Icon(Icons.Default.AccountCircle, contentDescription = null, tint = Color.White)
            },
            label = { Text(stringResource(id = R.string.profile),style = TextStyle(color = Color.White)) }
        )
        BottomNavigationItem(
            selected = selectedTab == choreBuddyScreen.History,
            onClick = { onTabSelected(choreBuddyScreen.History) },
            icon = {
                Icon(Icons.Default.List, contentDescription = null, tint = Color.White)
            },
            label = { Text(stringResource(id = R.string.history),style = TextStyle(color = Color.White)) }
        )
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun chorebuddyv3App(
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()


    Scaffold(
        topBar = {
            val route = backStackEntry?.destination?.route
            val currentScreen = choreBuddyScreen.values().find { it.name == route } ?: choreBuddyScreen.HomePage

            choreBuddyAppBar(
                currentScreen = currentScreen,
                //canNavigateBack = navController.previousBackStackEntry != null,
                canNavigateBack = false,
                navigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = {
            val route = backStackEntry?.destination?.route
            val currentScreen = choreBuddyScreen.values().find { it.name == route } ?: choreBuddyScreen.HomePage

            MyBottomNavigationBar(
                selectedTab = currentScreen,
                onTabSelected = { screen ->
                    navController.navigate(screen.name){

                    }
                },
                navController = navController
            )
        }

    ) { innerPadding ->
        //val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = if (DataSource.getAllChoresFromList().isNotEmpty()) "HomePage" else "IntroPageScreen",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = "IntroPageScreen") {
                IntroPageScreen(navController = navController)
            }

            composable(route = "HomePage") {
                HomePage(navController = navController)
            }


            composable(route = "MyProfile") {
                MyProfile(navController = navController)
            }

            composable(route = "AddChore") {
                AddChore(navController = navController)
            }

            composable(route = "History") {
                History(navController = navController)
            }

            composable(route = "ChoreDetail/{selectedChoreId}"){ backStackEntry ->
                val choreID = backStackEntry.arguments?.getString("selectedChoreId")
                    ?: throw IllegalArgumentException("User ID not found")
                System.out.println("selectedChoreId show: "+ choreID)
                ChoreDetail(
                    navController = navController,
                    choreId = choreID,
                    onUpdateChore = { choreId, category, details, dueDate, progress ->
                        DataSource.updateChore(choreId, category, details, dueDate, progress)
                    },
                    onMarkAsDone = { choreId ->
                        // Mark chore as done logic here
                        DataSource.markChoreAsDone(choreId)
                    }
                )
            }

//            composable("ChoreDetail") {
//                val choreDetailViewModel: ChoreDetailViewModel = viewModel()
//                val choreId by remember(choreDetailViewModel.selectedChoreId) { mutableStateOf<String?>(null) }
//
//                System.out.println("choreId: "+choreId)
//                //val choreIdState = choreDetailViewModel.selectedChoreId.collectAsState()
//                val choreIdState = choreDetailViewModel.selectedChoreId.collectAsState()
//                System.out.println("choreIdState: "+ choreIdState)
//                //val choreId = choreIdState.value
//                if (choreIdState != null) {
//                    System.out.println("ChoreID in choreBuddyScreen is "+ choreId)
//                    // choreIdState contains a non-null value
//                    // Perform actions accordingly
//                } else {
//                    System.out.println("ChoreID in choreBuddyScreen is nothing")
//                    // choreIdState does not contain a value
//                    // Perform actions accordingly
//                }
//                ChoreDetail(navController, choreId)
//            }
//            composable(route = "ChoreDetail") { backStackEntry ->
//                val choreId = backStackEntry.arguments?.getString("choreId") ?: return@composable
//                ChoreDetail(navController = navController, choreId = choreId)
//            }

//            composable(route = "ChoreAddedPage") {
//                ChoreAddedPage(navController = navController)
//            }
//            composable(route = "ChoreAddedPage/{category}/{details}/{dueDate}") { backStackEntry ->
//                val category = backStackEntry.arguments?.getString("category") ?: throw IllegalArgumentException("category not found")
//                val details = backStackEntry.arguments?.getString("details") ?: throw IllegalArgumentException("details not found")
//                val dueDate = backStackEntry.arguments?.getString("dueDate") ?: throw IllegalArgumentException("dueDate not found")
//
//                ChoreAddedPage(navController = navController, category = category, details = details, dueDate = dueDate)
//            }


        }
    }
}



@Preview(showBackground = true)
@Composable
fun choreBuddyAppPreview2() {
    Chorebuddyv3Theme {
        chorebuddyv3App()
    }
}