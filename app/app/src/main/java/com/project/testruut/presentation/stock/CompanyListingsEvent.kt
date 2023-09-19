package com.project.testruut.presentation.stock

sealed class CompanyListingsEvent {
    object Refresh: CompanyListingsEvent()
}