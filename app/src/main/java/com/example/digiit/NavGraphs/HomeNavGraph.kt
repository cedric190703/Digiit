package com.example.digiit

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.digiit.Home.*
import com.example.digiit.NavGraphs.Graph

@Composable
fun HomeNavGraph(
    navController: NavHostController,
    padding: PaddingValues
    ) {
    NavHost(
        navController = navController,
        modifier = Modifier.padding(paddingValues = padding),
        route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(
            )
        }
        composable(route = BottomBarScreen.Wallet.route) {
            WalletScreen(
            )
        }
        composable(route = BottomBarScreen.Data.route) {
            DataScreen(
            )
        }
        composable(route = BottomBarScreen.Menu.route) {
            MenuScreen(
            )
        }
    }
}