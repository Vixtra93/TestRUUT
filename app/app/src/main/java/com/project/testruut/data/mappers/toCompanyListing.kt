package com.project.testruut.data.mappers

import com.project.testruut.data.local.CompanyListingEntity
import com.project.testruut.data.local.UserEntity
import com.project.testruut.domain.model.CompanyListing
import com.project.testruut.domain.model.User

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(
        symbol = symbol,
        series = series,
        open = open,
        low = low,
        high = high,
        close = close
    )
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(
        symbol = symbol,
        series = series,
        open = open,
        low = low,
        high = high,
        close = close
    )
}

fun User.toUserEntity(): UserEntity {
    return UserEntity(
        userName = userName,
        email = email,
        password = password
    )
}

fun UserEntity.toUser(): User {
    return User(
        userName = userName,
        email = email,
        password = password
    )
}