package com.project.testruut.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    val userName: String,
    val email: String,
    val password: String,
    @PrimaryKey val id: Int? = null
)