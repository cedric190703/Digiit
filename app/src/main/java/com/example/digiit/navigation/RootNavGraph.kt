package com.example.digiit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.digiit.ApplicationData
import com.example.digiit.home.HomeScreen


@Composable
fun RootNavigationGraph(
    auth: ApplicationData
) {
    val start = if (auth.userProvider.user == null) Routes.AUTH.route else Routes.HOME.route

    val navController = rememberNavController()
    auth.navigation.controller = navController

    NavHost(
        navController = navController,
        route = "root",
        startDestination = start
    ) {
        composable(route = Routes.AUTH.route) {
            AuthNavGraph(auth)
        }
        composable(route = Routes.HOME.route) {
            HomeScreen(auth)
        }
    }
}
