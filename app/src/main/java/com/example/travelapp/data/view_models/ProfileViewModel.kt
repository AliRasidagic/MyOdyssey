package com.example.travelapp.data.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelapp.data.graph.Graph
import com.example.travelapp.data.repository.OfflineRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: OfflineRepository = Graph.repository
) : ViewModel() {

    var state by mutableStateOf(ProfileUiState())

    fun onUsernameChange(newUsername: String){
        state = state.copy(username = newUsername)
    }

    fun onPasswordChange(newPassword: String){
        state = state.copy(password = newPassword)
    }

    suspend fun getImage(): String {
        return repository.getImage()
    }

    fun addImage(image: String) {
        viewModelScope.launch {
            repository.insertImage(image)
        }
    }

    fun updateUsername(username: String) {
        viewModelScope.launch {
            repository.updateUsername(username)
        }
    }

    fun updatePassword(password: String) {
        viewModelScope.launch {
            repository.updatePassword(password)
        }
    }

    suspend fun refreshData() {
        state = state.copy(
            continents = repository.countContinents(),
            countries = repository.countCountries(),
            cities = repository.countCities(),
            trips = repository.countTrips(),
            username = repository.getUsername(),
            password = repository.getPassword(),
            countriesList = repository.getCountries()
        )
    }

    init {
        viewModelScope.launch {
            refreshData()
        }
    }
}

data class ProfileUiState(
    var continents: Int = 0,
    var countries: Int = 0,
    var cities: Int = 0,
    var trips: Int = 0,
    var username: String? = null,
    var password: String? = null,
    var countriesList: List<String> = emptyList()
)