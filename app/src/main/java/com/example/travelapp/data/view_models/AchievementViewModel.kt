package com.example.travelapp.data.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelapp.data.graph.Graph
import com.example.travelapp.data.repository.OfflineRepository
import kotlinx.coroutines.launch

class AchievementViewModel(
    private val repository: OfflineRepository = Graph.repository
) : ViewModel() {

    var state by mutableStateOf(AchievementUiState())

    init {
        viewModelScope.launch {
            state = state.copy(
                continents = repository.countContinents(),
                countries = repository.countCountries(),
                cities = repository.countCities(),
                trips = repository.countTrips()
            )
        }
    }
}

data class AchievementUiState(
    var continents: Int = 0,
    var countries: Int = 0,
    var cities: Int = 0,
    var trips: Int = 0
)