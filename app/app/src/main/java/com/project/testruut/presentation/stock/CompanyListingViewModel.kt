package com.project.testruut.presentation.stock

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
class CompanyListingViewModel @Inject constructor(
    private val repository: StockRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    var state by mutableStateOf(CompanyListingStateUI())

    fun onEvent(event: CompanyListingsEvent) {
        when (event) {
            is CompanyListingsEvent.Refresh -> {
                getCompanyListings(fetchRemote = true)
            }
        }
    }

    private fun getCompanyListings(
        fetchRemote: Boolean = false
    ) {
        viewModelScope.launch {
            repository
                .getCompanyListings(fetchRemote)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { listtings ->
                                state = state.copy(
                                    companies = listtings
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

    fun isValidAutentication(navToLogin:()->Unit) {
        if (firebaseAuth.currentUser?.uid != null){
            getCompanyListings()
        }else{
            navToLogin()
        }
    }

}