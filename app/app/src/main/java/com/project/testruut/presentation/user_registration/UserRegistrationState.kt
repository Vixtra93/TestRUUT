package com.project.testruut.presentation.user_registration

import com.project.testruut.util.Resource

data class UserRegistrationState(
    var userName: String = "",
    var email: String = "",
    var password: String = "",
    var userEnable: Boolean = false,
    var isLoading: Boolean = false
)
