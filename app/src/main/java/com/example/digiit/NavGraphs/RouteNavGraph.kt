package com.example.digiit.NavGraphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.digiit.SetHomeScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RootNavigationGraph(navController: NavHostController, auth: FirebaseAuth) {
    var sd = Graph.AUTHENTICATION
    if (auth.currentUser != null) {
        sd = Graph.HOME
    }

    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = sd
    ) {
        authNavGraph(navController = navController, auth = auth)
        composable(route = Graph.HOME) {
            SetHomeScreen()
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
}