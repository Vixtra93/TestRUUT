package com.project.testruut.presentation.user_registration

data class UserRegistrationStateUI(
    var userName: String = "",
    var isErrorUserName: Boolean = false,
    var email: String = "",
    var isErrorEmail: Boolean = false,
    var password: String = "",
    var isErrorPassword: Boolean = false,
    var userEnable: Boolean = false,
    var isLoading: Boolean = false
)
