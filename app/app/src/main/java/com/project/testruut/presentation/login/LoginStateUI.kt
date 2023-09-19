package com.project.testruut.presentation.login

data class LoginStateUI(
    val email: String = "",
    val password: String = "",
    val loginEnable: Boolean = false,
    val isLoading: Boolean = false
)
