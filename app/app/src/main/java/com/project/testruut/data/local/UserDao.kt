package com.project.testruut.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListings(user: UserEntity)

    @Query("DELETE FROM userentity")
    suspend fun clearCompanyListing()

    @Query("SELECT * FROM userentity where email = :email")
    suspend fun getUser(email: String): UserEntity

}