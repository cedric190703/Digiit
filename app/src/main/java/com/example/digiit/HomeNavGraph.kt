package com.example.digiit

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.digiit.Home.DataScreen
import com.example.digiit.Home.HomeScreen
import com.example.digiit.Home.MenuScreen
import com.example.digiit.Home.WalletScreen

@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues
) {

    NavHost(
        navController = navController,

        // set the start destination as home
        startDestination = "home",

        // Set the padding provided by scaffold
        modifier = Modifier.padding(paddingValues = padding),

        builder = {

            // route : Home
            composable("home") {
                HomeScreen()
            }

            // route : wallet
            composable("wallet") {
                WalletScreen()
            }

            // route : data
            composable("data") {
                DataScreen()
            }

            // route : Menu
            composable("menu") {
                MenuScreen()
            }
        })

}