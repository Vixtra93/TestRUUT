package com.project.testruut.presentation.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavItem(
    val baseRoute: String,
    val navArgs: List<NavArg> = emptyList()
) {
    val route = run{
        val argsKeys = navArgs.map { "{${it.key}}" }
        listOf(baseRoute).plus(argsKeys).joinToString (separator = "/")
    }

    val args = navArgs.map {
        navArgument(it.key){
            type = it.navType
        }
    }
    object Login : NavItem("login")
    object Home : NavItem("detail")
   object UserRegistration : NavItem("user_registration")
    object Profile : NavItem("profile")
}


enum class NavArg(val key: String, val navType: NavType<*>) {

}
