package com.project.testruut.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.testruut.data.repository.StockRepository
import com.project.testruut.util.Resource
import com.project.testruut.util.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: StockRepository) : ViewModel() {

    var state by mutableStateOf(LoginStateUI())
    private val _stateLogin = MutableLiveData<Resource<Unit>>()
    val stateLogin: LiveData<Resource<Unit>> = _stateLogin
    fun onLoginChange(email: String, password: String) {
        state = state.copy(
            email = email,
            password = password,
            isErrorEmail = email.isValidEmail().not() ,
            isErrorPassword = (password.length > 4).not(),
            loginEnable = email.isValidEmail() && password.length > 4
        )
    }


    suspend fun onLoginUser(email: String, password: String, navigateToHome: () -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            repository.onLoginUser(email, password).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        navigateToHome()
                    }

                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }

                    is Resource.Error -> {
                        _stateLogin.value = Resource.Error(
                            message = result.message
                                ?: "Ups! Ocurrió un problema al iniciar sesión",
                            null
                        )
                    }

                }

            }
        }
    }


}