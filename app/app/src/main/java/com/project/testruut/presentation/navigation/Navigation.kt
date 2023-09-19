import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.testruut.presentation.stock.CompanyListingsScreen
import com.project.testruut.presentation.login.UserLoginScreen
import com.project.testruut.presentation.profile.ProfileScreen
import com.project.testruut.presentation.user_registration.UserRegistrationScreen
import com.project.testruut.presentation.ui.navigation.NavItem

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavItem.Login.baseRoute) {
        composable(NavItem.Login.route) {
            UserLoginScreen(navigateToHome = {
                navController.navigate(NavItem.Home.route) {
                    popUpTo(NavItem.Login.route) {
                        inclusive = true
                    }
                }
            }, navigateToUserRegistration = {
                navController.navigate(NavItem.UserRegistration.route) {
                    popUpTo(NavItem.Login.route)
                    launchSingleTop = true
                }
            })
        }
        composable(NavItem.Home.route) {
            CompanyListingsScreen(navToProfile = {
                navController.navigate(NavItem.Profile.route)
            }, navToLogin = {
                navController.navigate(NavItem.Login.route) {
                    launchSingleTop = true
                    popUpTo(NavItem.Home.route) {
                        inclusive = true
                    }
                }
            })
        }
        composable(NavItem.UserRegistration.route) {
            UserRegistrationScreen(navigateToLogin = {
                navController.navigate(NavItem.Login.route) {
                    launchSingleTop = true
                    popUpTo(NavItem.Login.route) {
                        inclusive = true
                    }
                }
            })
        }

        composable(NavItem.Profile.route) {
            ProfileScreen(onBackPreset = {
                navController.navigateUp()
            }, navToLogin = {
                navController.navigate(NavItem.Login.route) {
                    launchSingleTop = true
                    popUpTo(NavItem.Profile.route) {
                        inclusive = true
                    }
                    popUpTo(NavItem.Home.route) {
                        inclusive = true
                    }
                }
            })
        }

    }

}