package com.project.testruut.data.repository

import android.content.res.AssetManager
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.project.testruut.domain.csv.CSVParser
import com.project.testruut.data.local.StockDatabase
import com.project.testruut.data.mappers.toCompanyListing
import com.project.testruut.data.mappers.toCompanyListingEntity
import com.project.testruut.data.mappers.toUser
import com.project.testruut.data.mappers.toUserEntity
import com.project.testruut.data.remote.StockApi
import com.project.testruut.domain.model.CompanyListing
import com.project.testruut.domain.model.User
import com.project.testruut.util.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImp @Inject constructor(
    private val api: StockApi,
    db: StockDatabase,
    private val firebaseAuth: FirebaseAuth,
    private val assetManager: AssetManager,
    private val companyListingParser: CSVParser<CompanyListing>


) : StockRepository {

    private val daoCompany = db.stockDao
    private val daoUser = db.userDao
    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListing = daoCompany.searchCompanyListing()
            emit(Resource.Success(
                data = localListing.map { it.toCompanyListing() }
            ))

            val isDbEmpty = localListing.isEmpty()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val listingStock = try {
                //val response = api.getListStrings()
                companyListingParser.parse(assetManager.open("stocks_data.csv"))


            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Couldn't load data"))
                null
            }

            listingStock?.let { listing ->
                daoCompany.clearCompanyListing()
                daoCompany.insertCompanyListings(listing.map { it.toCompanyListingEntity() })
                emit(
                    Resource.Success(
                        data = daoCompany
                            .searchCompanyListing()
                            .map { it.toCompanyListing() })
                )
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun setUserRegistration(
        user: User
    ): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading(true))
            user.let {
                daoUser.insertCompanyListings(it.toUserEntity())
                emit(Resource.Success(Unit))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun createUserAndPassword(user: User): Flow<Resource<Unit>> = channelFlow {
        send(Resource.Loading(true))
        firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    launch {
                        send(Resource.Success(Unit))
                    }
                } else {
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthUserCollisionException) {
                        launch {
                            send(
                                Resource.Error(
                                    message = "${e.message}",
                                    data = null
                                )
                            )
                        }
                        Log.d("login", "failed ${e.message}")
                    }
                }
            }
        send(Resource.Loading(false))
        awaitClose()
    }

    override suspend fun onLoginUser(email: String, password: String): Flow<Resource<Unit>> =
        channelFlow {
            send(Resource.Loading(true))
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        launch {
                            send(Resource.Success(Unit))
                        }
                    }
                }.addOnFailureListener { e ->
                    launch {
                        send(Resource.Error(message = "${e.message}"))
                    }
                    Log.d("login", "failed ${e.message}")
                }
            send(Resource.Loading(false))
            awaitClose()

        }

    override suspend fun getCurrentUser(email: String): Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading(true))
            emit(
                Resource.Success(
                    data = daoUser.getUser(email).toUser()
                )
            )
            emit(Resource.Loading(false))
        }
    }


}


