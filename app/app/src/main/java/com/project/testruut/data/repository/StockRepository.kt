package com.project.testruut.data.repository

import com.project.testruut.domain.model.CompanyListing
import com.project.testruut.domain.model.User
import com.project.testruut.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun getCompanyListings(
        fetchFromRemote: Boolean
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun setUserRegistration(
        user: User
    ): Flow<Resource<Unit>>

    suspend fun createUserAndPassword(
        user: User
    ):Flow<Resource<Unit>>

    suspend fun onLoginUser(email: String, password:String) : Flow<Resource<Unit>>

    suspend fun getCurrentUser(email: String): Flow<Resource<User>>


}