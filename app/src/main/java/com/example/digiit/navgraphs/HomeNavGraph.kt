package com.example.digiit.navgraphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.digiit.data.UserProvider
import com.example.digiit.data.user.User
import com.example.digiit.home.*
import com.example.digiit.navgraphs.Graph

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
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(auth)
        }
        composable(route = BottomBarScreen.Wallet.route) {
            WalletScreen(auth)
        }
        composable(route = BottomBarScreen.Data.route) {
            DataScreen(auth)
        }
        composable(route = BottomBarScreen.Menu.route) {
            MenuScreen(loginNavController, auth)
        }
    }
}
