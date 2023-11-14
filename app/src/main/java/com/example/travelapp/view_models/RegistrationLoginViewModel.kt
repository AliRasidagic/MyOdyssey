package com.example.travelapp.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelapp.data.LoginInfo
import com.example.travelapp.graph.Graph
import com.example.travelapp.repository.OfflineRepository
import kotlinx.coroutines.launch

class RegistrationLoginViewModel(
    private val repository: OfflineRepository = Graph.repository
) : ViewModel() {

    var state by mutableStateOf(RegistrationLoginUiState())
        private set

    fun onNameChange(newName: String){
        state = state.copy(username = newName)
    }

    fun onEmailChange(newEmail: String){
        state = state.copy(email = newEmail)
    }

    fun onPasswordChange(newPassword: String){
        state = state.copy(password = newPassword)
    }

    fun onConfirmChange(newConfirm: String){
        state = state.copy(confirmPassword = newConfirm)
    }

    fun addUser(loginInfo: LoginInfo) {
        viewModelScope.launch {
            repository.insertUser(loginInfo)
        }
    }

    suspend fun checkUserByEmailRegister(email: String): Boolean {
        val user = repository.getUserWithEmail(email)
        return user != null
    }

    suspend fun checkUserByEmailLogin(email: String): LoginInfo? {
        return repository.getUserWithEmail(email)
    }
}

data class RegistrationLoginUiState(
    val username: String? = null,
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isChecked: Boolean = true
)
