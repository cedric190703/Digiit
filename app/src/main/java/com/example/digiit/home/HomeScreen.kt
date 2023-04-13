package com.example.digiit.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.digiit.data.UserProvider
import com.example.digiit.navgraphs.HomeNavGraph

@Composable
fun SetHomeScreen(auth: UserProvider, navControllerLogin: NavHostController) {
    val navControllerHome = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController = navControllerHome) }
    ) {padding ->
        HomeNavGraph(navController = navControllerHome,
            padding = padding,
            auth,
            loginNavController = navControllerLogin)
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Wallet,
        BottomBarScreen.Data,
        BottomBarScreen.Menu
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        BottomAppBar(
            backgroundColor = Color.White,
            cutoutShape = RoundedCornerShape(16.dp),
            elevation = 80.dp,
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth()//.clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
                .shadow(elevation = 8.dp, shape = RectangleShape)


        ) {
            screens.forEach { screen ->
                BottomNavigationItem(
                    selected = currentDestination?.hierarchy?.any {
                        it.route == screen.route
                    } == true,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            if (currentDestination?.hierarchy?.any { it.route == screen.route } == true)
                                screen.full_icon else screen.icon,
                        contentDescription = screen.title,
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier.size(28.dp)
                    )
                    },
                    selectedContentColor = MaterialTheme.colors.primary,
                    unselectedContentColor = MaterialTheme.colors.primary,
                    alwaysShowLabel = false, // hide label when unselected
                    label = {
                        if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
                            Text(screen.title)
                        }
                    }
                )
            }
        }
    }
}