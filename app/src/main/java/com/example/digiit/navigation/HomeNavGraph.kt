package com.example.digiit.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.digiit.ApplicationData
import com.example.digiit.home.*
import com.example.digiit.screens.MenuScreen


@Composable
fun HomeNavGraph(
    auth: ApplicationData,
    padding: PaddingValues
) {
    AppNavHost(nav = auth.navigation, route = Routes.HOME.route, default = Routes.TICKETS.route) {
        composable(route = Routes.TICKETS.route) {
            TicketsScreen(auth)
        }
        composable(route = Routes.WALLETS.route) {
            WalletsScreen(auth)
        }
        composable(route = Routes.DATA.route) {
            DataScreen(auth)
        }
        composable(route = Routes.MENU.route) {
            MenuScreen(auth)
        }
    }
}
