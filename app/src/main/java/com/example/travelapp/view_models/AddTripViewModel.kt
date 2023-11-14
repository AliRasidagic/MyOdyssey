package com.example.travelapp.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelapp.data.TravelInfo
import com.example.travelapp.graph.Graph
import com.example.travelapp.repository.OfflineRepository
import kotlinx.coroutines.launch

class AddTripViewModel(
    private val repository: OfflineRepository = Graph.repository
) : ViewModel() {

    var state by mutableStateOf(AddTripUiState())
        private set

    fun onStartDateChange(newDate: String){
        state = state.copy(startDate = newDate)
    }

    fun onEndDateChange(newDate: String){
        state = state.copy(endDate = newDate)
    }

    fun onContinentChange(newContinent: String){
        state = state.copy(selectedContinent = newContinent)
    }

    fun onCountryChange(newCountry: String){
        state = state.copy(selectedCountry = newCountry)
    }

    fun onShowChange(newShow: Boolean){
        state = state.copy(showDialog = newShow)
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
    val selectedContinent: String = "Continent",
    val selectedCountry: String = "Country",
    val startDate: String = "Start Date",
    val endDate: String = "End Date",
    var showDialog: Boolean = false
)