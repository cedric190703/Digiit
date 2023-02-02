package com.example.digiit

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.digiit.navigation.homeLogin
import com.example.digiit.navigation.lostPassword

@Composable
fun SetNavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController,
    startDestination = Screen.Splash.route)
    {
        composable(route = Screen.Splash.route)
        {
            AnimatedSplashScreen(navController = navController)
        }

        composable(route = Screen.HomeLogin.route)
        {
            homeLogin(navController)
        }

        composable(route = Screen.LostPassword.route)
        {
            lostPassword(navController)
        }

        composable(route = Screen.Home.route)
        {
            HomeScreen()
        }

        composable(route = Screen.Wallet.route)
        {
            WalletScreen()
        }
    }
}