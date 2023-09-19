package com.project.testruut.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CompanyListingEntity::class, UserEntity::class],
    version = 1
)
abstract class StockDatabase : RoomDatabase() {

    abstract val stockDao : StockDao
    abstract val userDao : UserDao

}