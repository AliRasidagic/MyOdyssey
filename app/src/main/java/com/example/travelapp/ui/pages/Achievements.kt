package com.example.travelapp.ui.pages

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelapp.R
import com.example.travelapp.data.view_models.AchievementViewModel

@Composable
fun Achievements(
    modifier: Modifier = Modifier,
    viewModel: AchievementViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                val achievements = achievementList(viewModel)
                achievements
                    .sortedBy { it.isCompleted }
                    .forEach { achievement ->
                    AchievementCard(
                        name = achievement.name,
                        image = achievement.imageResId,
                        description = achievement.description,
                        isCompleted = achievement.isCompleted
                    )
                }
            }
        }
    }
}

@Composable
fun AchievementCard(
    name: String,
    image: Int,
    description: String,
    isCompleted: Boolean
) {
    var isExpanded by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if(isExpanded) 180f else 0f, label = ""
    )

    Card(
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(20.dp))
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        colors = CardDefaults.cardColors(containerColor = (if(isCompleted) Color.Green else Color.White)),
        onClick = {
            isExpanded = !isExpanded
        }
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = image),
                    contentDescription = "Travel Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(60.dp)
                        .weight(1f)
                        .clip(CircleShape)
                )
                Text(
                    text = name,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                    modifier = Modifier
                        .weight(4f)
                        .padding(start = 8.dp),
                    color = Color.Black
                )
                IconButton(
                    onClick = {
                        isExpanded = !isExpanded
                    },
                    modifier = Modifier
                        .weight(1f)
                        .rotate(rotationState)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }
            if (isExpanded) {
                Text(
                    text = description,
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun achievementList(viewModel: AchievementViewModel): List<Achievement> {
    val uiState = viewModel.state

    return listOf(
        Achievement("5 Trips", "Complete 5 trips", uiState.trips > 4, R.drawable.gigachad),
        Achievement("10 Trips", "Complete 10 trips", uiState.trips > 9, R.drawable.gigachad),
        Achievement("20 Trips", "Complete 20 trips", uiState.trips > 19, R.drawable.gigachad),
        Achievement("50 Trips", "Complete 50 trips", uiState.trips > 49, R.drawable.gigachad),
        Achievement("100 Trips", "Complete 100 trips", uiState.trips > 99, R.drawable.gigachad),
        Achievement("200 Trips", "Complete 200 trips", uiState.trips > 199, R.drawable.gigachad),
        Achievement("500 Trips", "Complete 500 trips", uiState.trips > 499, R.drawable.gigachad),

        Achievement("5 Cities", "Complete 5 cities", uiState.cities > 4, R.drawable.gigachad),
        Achievement("10 Cities", "Complete 10 cities", uiState.cities > 9, R.drawable.gigachad),
        Achievement("20 Cities", "Complete 20 cities", uiState.cities > 19, R.drawable.gigachad),
        Achievement("50 Cities", "Complete 50 cities", uiState.cities > 49, R.drawable.gigachad),
        Achievement("100 Cities", "Complete 100 cities", uiState.cities > 99, R.drawable.gigachad),
        Achievement("200 Cities", "Complete 200 cities", uiState.cities > 199, R.drawable.gigachad),
        Achievement("500 Cities", "Complete 500 cities", uiState.cities > 499, R.drawable.gigachad),

        Achievement("5 Countries", "Visit 5 countries", uiState.countries > 4, R.drawable.trips5),
        Achievement("10 Countries", "Visit 10 countries", uiState.countries > 9, R.drawable.gigachad),
        Achievement("20 Countries", "Visit 20 countries", uiState.countries > 19, R.drawable.gigachad),
        Achievement("50 Countries", "Visit 50 countries", uiState.countries > 49, R.drawable.gigachad),
        Achievement("100 Countries", "Visit 100 countries", uiState.countries > 99, R.drawable.gigachad),
        Achievement("All Countries", "Visit All countries", uiState.countries > 205, R.drawable.gigachad),

        Achievement("3 Continents", "Visit 3 continents", uiState.continents > 2, R.drawable.gigachad),
        Achievement("All Continents", "Visit all continents", uiState.continents > 5, R.drawable.gigachad),

        Achievement("Europe", "Travel to all the countries in Europe", uiState.trips > 25, R.drawable.gigachad),
        Achievement("Asia", "Travel to all the countries in Asia", uiState.trips > 25, R.drawable.gigachad),
        Achievement("Africa", "Travel to all the countries in Africa", uiState.trips > 25, R.drawable.gigachad),
        Achievement("North America", "Travel to all the countries in North America", uiState.trips > 25, R.drawable.gigachad),
        Achievement("South America", "Travel to all the countries in South America", uiState.trips > 25, R.drawable.gigachad),
        Achievement("Oceania", "Travel to all the countries in Oceania", uiState.trips > 25, R.drawable.gigachad),

        Achievement("Tour de France", "Have 5 trips in France", uiState.trips > 25, R.drawable.gigachad),
        Achievement("USSR", "Visit all countries of the former Soviet Union", uiState.trips > 25, R.drawable.gigachad),
        Achievement("Polar Bears", "Visit Greenland", uiState.trips > 25, R.drawable.gigachad),
        Achievement("Peacemaker", "Visit North and South Korea", uiState.trips > 25, R.drawable.gigachad),
        Achievement("Yugoslavia", "Visit all EX-YU countries", uiState.trips > 25, R.drawable.gigachad)
    )
}

data class Achievement(
    val name: String,
    val description: String,
    var isCompleted: Boolean,
    val imageResId: Int
)