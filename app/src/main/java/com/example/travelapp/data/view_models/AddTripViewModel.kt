package com.example.travelapp.data.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelapp.data.database.TravelInfo
import com.example.travelapp.data.graph.Graph
import com.example.travelapp.data.repository.OfflineRepository
import kotlinx.coroutines.launch

class AddTripViewModel(
    private val repository: OfflineRepository = Graph.repository
) : ViewModel() {

    var state by mutableStateOf(AddTripUiState())

    fun onContinentChange(newContinent: String){
        state = state.copy(selectedContinent = newContinent)
    }

    fun onCountryChange(newCountry: String){
        state = state.copy(selectedCountry = newCountry)
    }

    fun addTrip(travelInfo: TravelInfo) {
        viewModelScope.launch {
            repository.insertTrip(travelInfo)
        }
    }

    suspend fun getTrips(): List<TravelInfo> {
        return repository.getTrips()
    }
}

data class AddTripUiState(
    var selectedContinent: String = "Continent",
    var selectedCountry: String = "Country",
    var startDate: String = "Start Date",
    var endDate: String = "End Date",
)