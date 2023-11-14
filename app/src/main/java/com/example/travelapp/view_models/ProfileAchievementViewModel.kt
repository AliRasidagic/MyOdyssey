package com.example.travelapp.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelapp.data.LoginInfo
import com.example.travelapp.graph.Graph
import com.example.travelapp.repository.OfflineRepository
import kotlinx.coroutines.launch

class ProfileAchievementViewModel(
    private val repository: OfflineRepository = Graph.repository
) : ViewModel() {
    var continents: Int = 0
        private set

    var countries: Int = 0
        private set

    var cities: Int = 0
        private set

    var trips: Int = 0
        private set

    var username: String? = null
        private set

    var countriesList: List<String> = emptyList()
        private set

    fun getImage(): String {
        return repository.getImage()
    }

    fun addImage(image: String) {
        viewModelScope.launch {
            repository.insertImage(image)
        }
    }

    suspend fun refreshData() {
        continents = repository.countContinents()
        countries = repository.countCountries()
        cities = repository.countCities()
        trips = repository.countTrips()
        username = repository.getUsername()
        countriesList = repository.getCountries()
    }

    init {
        viewModelScope.launch {
            continents = repository.countContinents()
            countries = repository.countCountries()
            cities = repository.countCities()
            trips = repository.countTrips()
            username = repository.getUsername()
            countriesList = repository.getCountries()
        }
    }
}
