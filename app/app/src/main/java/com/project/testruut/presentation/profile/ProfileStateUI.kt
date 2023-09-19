package com.project.testruut.presentation.profile

import com.project.testruut.domain.model.User

data class ProfileStateUI(
    val userProfile: User? = null,
    val isLoading: Boolean = false,
)
