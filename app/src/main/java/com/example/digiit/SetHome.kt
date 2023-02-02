package com.example.digiit

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun SetHome(navController: NavHostController) {
    Scaffold(
        // Bottom navigation
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }, content = { padding ->
            // Navhost: where screens are placed
            NavHostContainer(navController = navController, padding = padding)
        }
    )
}