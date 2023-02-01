package com.example.digiit

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetNavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController,
    startDestination = Screen.Splash.route)
    {
        composable(route = Screen.Home.route)
        {
            HomeScreen()
        }

        composable(route = Screen.Splash.route)
        {
            AnimatedSplashScreen(navController = navController)
        }

        composable(route = Screen.Wallet.route)
        {
            WalletScreen()
        }
    }
}