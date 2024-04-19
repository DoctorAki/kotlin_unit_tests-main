package com.example.unit_tests.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.unit_tests.presentation.screens.auth.AuthScreen
import com.example.unit_tests.presentation.screens.list.UsersListScreen

@Composable
fun Navigation(
    navHostController: NavHostController,
    getStartDestination: String,
) {
    NavHost(
        navController = navHostController,
        startDestination = getStartDestination
    ) {
        composable("Auth") {
            AuthScreen(navHostController)
        }
        composable("UsersList") {
            UsersListScreen(navHostController)
        }
    }
}