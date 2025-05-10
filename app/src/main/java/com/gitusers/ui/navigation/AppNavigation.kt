package com.gitusers.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gitusers.ui.navigation.AppNavigationScreen.UserDetailScreen
import com.gitusers.ui.navigation.AppNavigationScreen.UserListScreen
import com.gitusers.ui.screens.userdetail.UserDetailScreen
import com.gitusers.ui.screens.userlist.UserListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = UserListScreen.route) {
        composable(UserListScreen.route) { UserListScreen(navController) }
        composable(
            route = UserDetailScreen.route,
            arguments = listOf(navArgument("userName") { type = NavType.StringType })
        ) {
            UserDetailScreen(
                navController = navController
            )
        }
    }
}

sealed class AppNavigationScreen(val route: String) {
    data object UserListScreen : AppNavigationScreen(route = "userList")
    data object UserDetailScreen : AppNavigationScreen(route = "userDetail/{userName}") {
        fun createRoute(userName: String) = "userDetail/$userName"
    }
}