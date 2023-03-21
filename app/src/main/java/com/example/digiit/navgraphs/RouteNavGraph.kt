package com.example.digiit.navgraphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.digiit.data.UserProvider
import com.example.digiit.home.SetHomeScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RootNavigationGraph(navController: NavHostController, auth: UserProvider) {
    var sd = Graph.AUTHENTICATION
    if (auth.user != null) {
        sd = Graph.HOME
    }

    val context = LocalContext.current

    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = sd
    ) {
        authNavGraph(navController = navController, auth = auth, context = context)
        composable(route = Graph.HOME) {
            SetHomeScreen(auth)
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
}