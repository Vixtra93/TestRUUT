package com.project.testruut.presentation.login

data class LoginStateUI(
    val email: String = "",
    val password: String = "",
    val isErrorPassword: Boolean = false,
    val isErrorEmail: Boolean = false,
    val loginEnable: Boolean = false,
    val isLoading: Boolean = false
)
