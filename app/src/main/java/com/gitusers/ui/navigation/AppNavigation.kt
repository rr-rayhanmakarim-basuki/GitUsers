package com.gitusers.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gitusers.ui.navigation.AppNavigationScreenList.USER_LIST_DETAIL
import com.gitusers.ui.navigation.AppNavigationScreenList.USER_LIST_SCREEN
import com.gitusers.ui.screens.userdetail.UserDetailScreen
import com.gitusers.ui.screens.userlist.UserListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = USER_LIST_SCREEN.route) {
        composable(USER_LIST_SCREEN.route) { UserListScreen(navController) }
        composable(USER_LIST_DETAIL.route) { UserDetailScreen(navController) }
    }
}

enum class AppNavigationScreenList(val route: String) {
    USER_LIST_SCREEN(route = "userList"),
    USER_LIST_DETAIL(route = "userDetail")
}