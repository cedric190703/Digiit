package com.example.digiit.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.digiit.ApplicationData
import com.example.digiit.navigation.HomeNavGraph


@Composable
fun HomeScreen(auth: ApplicationData) {
    Scaffold(
        bottomBar = { HomeBottomBar(auth) }
    ) {padding ->
        HomeNavGraph(auth, padding)
    }
}


@Composable
fun HomeBottomBar(auth: ApplicationData) {
    val screens = listOf(
        HomeBottomBarEntry.Ticket,
        HomeBottomBarEntry.Wallet,
        HomeBottomBarEntry.Data,
        HomeBottomBarEntry.Menu
    )
    val currentDestination = auth.navigation.currentPath()

    val bottomBarDestination = screens.any { it.path == currentDestination }
    if (bottomBarDestination) {
        BottomAppBar(
            backgroundColor = MaterialTheme.colors.background,
            cutoutShape = RoundedCornerShape(16.dp),
            elevation = 80.dp,
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth()//.clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
                .shadow(elevation = 8.dp, shape = RectangleShape)


        ) {
            screens.forEach { screen ->
                val selected = currentDestination == screen.path
                BottomNavigationItem(
                    selected = selected,
                    onClick = {
                        auth.navigation.navigate(screen.path)
                        //navController.navigate(screen.route) {
                        //    popUpTo(navController.graph.findStartDestination().id)
                        //    launchSingleTop = true
                        //}
                    },
                    icon = {
                        Icon(if(selected) screen.full_icon else screen.icon,
                            contentDescription = screen.title,
                            tint = MaterialTheme.colors.primary,
                            modifier = Modifier.size(28.dp)
                        )
                    },
                    selectedContentColor = MaterialTheme.colors.primary,
                    unselectedContentColor = MaterialTheme.colors.primary,
                    alwaysShowLabel = false, // hide label when unselected
                    label = {
                        if (selected) {
                            Text(screen.title)
                        }
                    }
                )
            }
        }
    }
}