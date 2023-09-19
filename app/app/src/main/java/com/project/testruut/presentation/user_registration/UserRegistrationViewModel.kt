package com.project.testruut.presentation.user_registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.testruut.domain.model.User
import com.project.testruut.data.repository.StockRepository
import com.project.testruut.util.Resource
import com.project.testruut.util.isValidEmail
import com.project.testruut.util.isValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserRegistrationViewModel @Inject constructor(
    val repository: StockRepository
) :
    ViewModel() {


    var state by mutableStateOf(UserRegistrationState())

    private val _stateStatus = MutableLiveData<Resource<Unit>>()
    val stateStatus: LiveData<Resource<Unit>> = _stateStatus
    private fun isValidUserName(userName: String): Boolean {
        return userName.length > 5
    }

    fun onRegistrationUserChange(userName: String, email: String, password: String) {
        state = state.copy(
            userName = userName, email = email, password = password,
            userEnable = isValidUserName(userName) && email.isValidEmail() && password.isValidPassword()
        )

    }

    fun onRegistrationSelected(user: User, navigateToLogin: () -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            repository
                .createUserAndPassword(user)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            setUserDb(user, navigateToLogin)
                        }

                        is Resource.Error -> _stateStatus.postValue(
                            Resource.Error(message = result.message ?: "Ocurrió un error")
                        )

                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                    }

                }
        }
    }

    private suspend fun setUserDb(user: User, navigateToLogin: () -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            repository.setUserRegistration(user).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _stateStatus.value = Resource.Success(Unit)
                        navigateToLogin()
                    }

                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)

                    }

                    is Resource.Error -> {
                        _stateStatus.value = Resource.Error(
                            message = result.message ?: "Ocurrió un error al crear el usuario", null
                        )
                    }

                }

            }
        }
    }


}