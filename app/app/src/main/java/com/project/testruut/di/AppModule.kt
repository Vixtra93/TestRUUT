package com.project.testruut.di

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.project.testruut.data.local.StockDatabase
import com.project.testruut.data.remote.StockApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStock(): StockApi {
        return Retrofit.Builder()
            .baseUrl(StockApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create()
    }

    @Provides
    @Singleton
    fun provideStockDatabase(app: Application): StockDatabase {
        return Room.databaseBuilder(
            app,
            StockDatabase::class.java,
            "stockdb.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuthClient(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideAssetManager(@ApplicationContext context: Context): AssetManager {
        return context.assets
    }



}