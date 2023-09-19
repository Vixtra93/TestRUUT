package com.project.testruut.presentation.stock

import com.project.testruut.domain.model.CompanyListing

data class CompanyListingStateUI(
    val companies: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
) 