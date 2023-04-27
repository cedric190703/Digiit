package com.example.digiit.navgraphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.digiit.data.UserProvider
import com.example.digiit.home.*
import com.example.digiit.screens.MenuScreen


@Composable
fun HomeNavGraph(
    navController: NavHostController,
    padding: PaddingValues,
    auth: UserProvider,
    loginNavController: NavHostController
) {
    NavHost(
        navController = navController,
        modifier = Modifier.padding(paddingValues = padding),
        route = Graph.HOME,
        startDestination = HomeBottomBarEntry.Home.route
    ) {
        composable(route = HomeBottomBarEntry.Home.route) {
            TicketsScreen(auth)
        }
        composable(route = HomeBottomBarEntry.Wallet.route) {
            WalletsScreen(auth)
        }
        composable(route = HomeBottomBarEntry.Data.route) {
            DataScreen(auth)
        }
        composable(route = HomeBottomBarEntry.Menu.route) {
            MenuScreen(loginNavController, auth)
        }
    }
}
