package com.project.testruut.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.project.testruut.data.repository.StockRepository
import com.project.testruut.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: StockRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    var state by mutableStateOf(ProfileStateUI())


    init {
        viewModelScope.launch {
            firebaseAuth.currentUser?.email?.let {
                getCurrenUserProfile(it)
            }
        }

    }

    private suspend fun getCurrenUserProfile(email: String) {
        viewModelScope.launch {
            repository.getCurrentUser(email)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { user ->
                                state = state.copy(
                                    userProfile = user
                                )
                            }
                        }

                        is Resource.Error -> Unit

                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                    }

                }
        }

    }

     fun userLogout(){
        FirebaseAuth.getInstance().signOut()
    }
}