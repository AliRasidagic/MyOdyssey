package com.example.travelapp.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.travelapp.ui.pages.Achievements
import com.example.travelapp.ui.pages.AddTrip
import com.example.travelapp.ui.pages.EditProfile
import com.example.travelapp.ui.pages.Login
import com.example.travelapp.ui.pages.Profile
import com.example.travelapp.ui.pages.Registration
import com.example.travelapp.ui.pages.TripInfo
import com.example.travelapp.ui.pages.Trips
import com.example.travelapp.data.view_models.AchievementViewModel
import com.example.travelapp.data.view_models.AddTripViewModel
import com.example.travelapp.data.view_models.ProfileViewModel
import com.example.travelapp.data.view_models.RegistrationLoginViewModel

enum class Navigation {
    Registration,
    Login,
    Profile,
    Trips,
    Achievements,
    EditProfile,
    TripInfo,
    AddTrip
}

@Composable
fun TravelApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Navigation.valueOf(
        backStackEntry?.destination?.route ?: Navigation.Profile.name
    )

    val registrationLoginViewModel = viewModel(modelClass = RegistrationLoginViewModel::class.java)
    val profileViewModel = viewModel(modelClass = ProfileViewModel::class.java)
    val achievementViewModel = viewModel(modelClass = AchievementViewModel::class.java)
    val addTripViewModel = viewModel(modelClass = AddTripViewModel::class.java)

    var navigationKey by remember { mutableIntStateOf(0) }

    LaunchedEffect(navigationKey) {
        profileViewModel.refreshData()
    }

    navController.currentBackStackEntry?.destination?.route?.hashCode()?.let {
        navigationKey = it
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (currentScreen != Navigation.Login && currentScreen != Navigation.Registration) {
            CustomTopAppBar(
                navController = navController,
                currentScreen = currentScreen,
                title = currentScreen
            )
        }
        NavHost(
            navController = navController,
            startDestination = Navigation.Profile.name,
            modifier = Modifier.weight(1f)
        ) {
            composable(route = Navigation.Registration.name) {
                Registration(
                    navController = navController,
                    viewModel = registrationLoginViewModel
                )
            }
            composable(route = Navigation.Login.name) {
                Login(
                    navController = navController,
                    viewModel = registrationLoginViewModel
                )
            }
            composable(route = Navigation.Profile.name) {
                Profile(
                    viewModel = profileViewModel
                )
            }
            composable(route = Navigation.Trips.name) {
                Trips(
                    navController = navController,
                    viewModel = addTripViewModel
                )
            }
            composable(route = Navigation.Achievements.name) {
                Achievements(
                    viewModel = achievementViewModel
                )
            }
            composable(route = Navigation.EditProfile.name) {
                EditProfile(
                    navController = navController,
                    viewModel = profileViewModel
                )
            }
            composable(route = Navigation.AddTrip.name) {
                AddTrip(
                    navController = navController,
                    viewModel = addTripViewModel
                )
            }
            composable(route = Navigation.TripInfo.name) {
                TripInfo()
            }
        }
        if (currentScreen != Navigation.Login && currentScreen != Navigation.Registration && currentScreen != Navigation.AddTrip && currentScreen != Navigation.EditProfile) {
            CustomBottomAppBar(
                navController = navController,
                currentScreen = currentScreen
            )
        }
    }
}

@Composable
fun CustomBottomAppBar(
    navController: NavController,
    currentScreen: Navigation
) {
    BottomAppBar(
        containerColor = Color.Black,
        contentColor = Color.White,
        modifier = Modifier
            .height(60.dp),
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                IconButton(
                    onClick = { navController.navigate(Navigation.Trips.name) },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Place,
                            contentDescription = null,
                            tint = if (currentScreen == Navigation.Trips) {
                                Color.Blue
                            } else {
                                Color.White
                            }
                        )
                        Text(
                            text = "Trips",
                            color = if (currentScreen == Navigation.Trips) {
                                Color.Blue
                            } else {
                                Color.White
                            },
                        )
                    }
                }
                IconButton(
                    onClick = { navController.navigate(Navigation.Profile.name) },
                    modifier = Modifier
                        .weight(1.5f)
                        .fillMaxHeight()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.PersonPin,
                            contentDescription = null,
                            tint = if (currentScreen == Navigation.Profile) {
                                Color.Blue
                            } else {
                                Color.White
                            }
                        )
                        Text(
                            text = "Profile",
                            color = if (currentScreen == Navigation.Profile) {
                                Color.Blue
                            } else {
                                Color.White
                            },
                        )
                    }
                }
                IconButton(
                    onClick = { navController.navigate(Navigation.Achievements.name) },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Filled.DoneAll,
                            contentDescription = null,
                            tint = if (currentScreen == Navigation.Achievements) {
                                Color.Blue
                            } else {
                                Color.White
                            }
                        )
                        Text(
                            text = "Trophies",
                            color = if (currentScreen == Navigation.Achievements) {
                                Color.Blue
                            } else {
                                Color.White
                            },
                        )
                    }
                }
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    navController: NavController,
    currentScreen: Navigation,
    title: Navigation
) {
    TopAppBar(
        title = {
            Text(
                text = title.toString(),
                color = Color.White,
                fontSize = 25.sp,
                modifier = Modifier
                    .padding(start = 8.dp)
            )
        },
        actions = {
            if (currentScreen == Navigation.Trips) {
                IconButton(
                    onClick = { navController.navigate(Navigation.AddTrip.name) },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            } else if (currentScreen == Navigation.Profile) {
                IconButton(
                    onClick = { navController.navigate(Navigation.EditProfile.name) },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            } else if (currentScreen == Navigation.Achievements) {
                Text(
                    text = "107/213",
                    color = Color.White,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Color.Black)
    )
}