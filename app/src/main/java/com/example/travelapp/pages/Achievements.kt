package com.example.travelapp.pages

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
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import com.example.travelapp.view_models.ProfileAchievementViewModel

@Composable
fun Achievements(
    modifier: Modifier = Modifier,
    viewModel: ProfileAchievementViewModel
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

@OptIn(ExperimentalMaterialApi::class)
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
        backgroundColor = if(isCompleted) Color.Green else Color.White,
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
fun achievementList(viewModel: ProfileAchievementViewModel): List<Achievement> {
    return listOf(
        Achievement("Beginner", "Travel more than 5 trips", viewModel.trips > 5, R.drawable.gigachad),
        Achievement("Intermediate", "Travel more than 10 trips", viewModel.trips > 10, R.drawable.gigachad),
        Achievement("Professional", "Travel more than 15 trips", viewModel.trips > 15, R.drawable.gigachad),
        Achievement("Colonial", "Visit more than 5 countries", viewModel.countries > 5, R.drawable.gigachad),
        Achievement("Mr. WorldWide", "Visit more than 5 continents", viewModel.continents > 5, R.drawable.gigachad),
        Achievement("Damn", "Travel more than 20 trips", viewModel.trips > 20, R.drawable.gigachad),
        Achievement("Hitchhiker", "Travel more than 25 trips", viewModel.trips > 25, R.drawable.gigachad),
        Achievement("Europe", "Travel to all the countries in Europe", viewModel.trips > 25, R.drawable.gigachad),
        Achievement("Asia", "Travel to all the countries in Asia", viewModel.trips > 25, R.drawable.gigachad),
        Achievement("Africa", "Travel to all the countries in Africa", viewModel.trips > 25, R.drawable.gigachad),
        Achievement("North America", "Travel to all the countries in North America", viewModel.trips > 25, R.drawable.gigachad),
        Achievement("South America", "Travel to all the countries in South America", viewModel.trips > 25, R.drawable.gigachad),
        Achievement("Oceania", "Travel to all the countries in Oceania", viewModel.trips > 25, R.drawable.gigachad)
    )
}

data class Achievement(
    val name: String,
    val description: String,
    var isCompleted: Boolean,
    val imageResId: Int
)