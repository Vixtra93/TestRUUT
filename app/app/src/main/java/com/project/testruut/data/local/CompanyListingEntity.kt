package com.project.testruut.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompanyListingEntity(
    val symbol: String,
    val series: String,
    val open:String,
    val high:String,
    val low:String,
    val close:String,
    @PrimaryKey val id: Int? = null
)
