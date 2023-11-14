package com.example.travelapp

import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
import com.example.travelapp.pages.Achievements
import com.example.travelapp.pages.AddTrip
import com.example.travelapp.pages.EditProfile
import com.example.travelapp.pages.Login
import com.example.travelapp.pages.Profile
import com.example.travelapp.pages.Registration
import com.example.travelapp.pages.TripInfo
import com.example.travelapp.pages.Trips
import com.example.travelapp.view_models.AddTripViewModel
import com.example.travelapp.view_models.ProfileAchievementViewModel
import com.example.travelapp.view_models.RegistrationLoginViewModel

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

//solve registration login start dest

@Composable
fun TravelApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Navigation.valueOf(
        backStackEntry?.destination?.route ?: Navigation.Profile.name
    )

    val registrationLoginRemember = viewModel(modelClass = RegistrationLoginViewModel::class.java)
    val registrationLoginViewModel = remember { registrationLoginRemember }

    val profileAchievementViewModel = viewModel(modelClass = ProfileAchievementViewModel::class.java)

    val addTripViewModel = viewModel(modelClass = AddTripViewModel::class.java)

    var navigationKey by remember { mutableIntStateOf(0) }

    LaunchedEffect(navigationKey) {
        profileAchievementViewModel.refreshData()
    }

    navController.currentBackStackEntry?.destination?.route?.hashCode()?.let {
        navigationKey = it
    }

    val startDestination =
        if (profileAchievementViewModel.username == null) {
            Navigation.Registration.name
        } else {
            Navigation.Login.name
        }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (currentScreen != Navigation.Login && currentScreen != Navigation.Registration) {
            CustomTopAppBar(
                navController = navController,
                currentScreen = currentScreen,
                title = currentScreen,
                viewModel = addTripViewModel
            )
        }
        NavHost(
            navController = navController,
            startDestination = startDestination,
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
                    viewModel = profileAchievementViewModel
                )
            }
            composable(route = Navigation.Trips.name) {
                Trips(
                    viewModel = addTripViewModel
                )
            }
            composable(route = Navigation.Achievements.name) {
                Achievements(
                    viewModel = profileAchievementViewModel
                )
            }
            composable(route = Navigation.EditProfile.name) {
                EditProfile()
            }
            composable(route = Navigation.AddTrip.name) {
                AddTrip(
                    viewModel = addTripViewModel
                )
            }
            composable(route = Navigation.TripInfo.name) {
                TripInfo()
            }
        }
        if (currentScreen != Navigation.Login && currentScreen != Navigation.Registration) {
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
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    BottomAppBar(
        backgroundColor = Color.Black,
        contentColor = Color.White,
        modifier = Modifier
            .height(60.dp),
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                IconButton(
                    onClick = { navController.navigate(Navigation.Trips.name) },
                    modifier = Modifier
                        .hoverable(interactionSource = interactionSource)
                        .weight(1f)
                    ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Place,
                            contentDescription = null,
                            modifier = Modifier.weight(0.6f),
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
                            modifier = Modifier.weight(0.4f)
                        )
                    }
                }
                IconButton(
                    onClick = { navController.navigate(Navigation.Profile.name) },
                    modifier = Modifier.weight(1.5f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.PersonPin,
                            contentDescription = null,
                            modifier = Modifier.weight(0.6f),
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
                            modifier = Modifier.weight(0.4f)
                        )
                    }
                }
                IconButton(
                    onClick = { navController.navigate(Navigation.Achievements.name) },
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Filled.DoneAll,
                            contentDescription = null,
                            modifier = Modifier.weight(0.6f),
                            tint = if (currentScreen == Navigation.Achievements) {
                                Color.Blue
                            } else {
                                Color.White
                            }
                        )
                        Text(
                            text = "Achievements",
                            color = if (currentScreen == Navigation.Achievements) {
                                Color.Blue
                            } else {
                                Color.White
                            },
                            modifier = Modifier.weight(0.4f)
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun CustomTopAppBar(
    navController: NavController,
    currentScreen: Navigation,
    title: Navigation,
    viewModel: AddTripViewModel
) {

    TopAppBar(
        backgroundColor = Color.Black
    ) {
        Text(
            text = title.toString(),
            color = Color.White,
            fontSize = 25.sp,
            modifier = Modifier
                .padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        if (currentScreen == Navigation.Trips) {
            IconButton(
                onClick = { viewModel.onShowChange(true) },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        } else if (currentScreen == Navigation.Profile){
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
        } else {
            Text(
                text = "107/213",
                color = Color.White
            )
        }
    }
}