package com.example.digiit.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Share
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.digiit.navigation.Routes

sealed class HomeBottomBarEntry(
    val path: String,
    val title: String,
    val icon: ImageVector,
    val full_icon : ImageVector
) {
    object Ticket : HomeBottomBarEntry(
        path = Routes.TICKETS.path,
        title = "Ticket",
        icon = Icons.Outlined.Home,
        full_icon = Icons.Rounded.Home
    )

    object Wallet : HomeBottomBarEntry(
        path = Routes.WALLETS.path,
        title = "Portefeuille",
        icon = Icons.Outlined.Email,
        full_icon = Icons.Rounded.Email
    )

    object Data : HomeBottomBarEntry(
        path = Routes.DATA.path,
        title = "Data",
        icon = Icons.Outlined.Share,
        full_icon = Icons.Rounded.Share
    )

    object Menu : HomeBottomBarEntry(
        path = Routes.MENU.path,
        title = "Menu",
        icon = Icons.Outlined.Menu,
        full_icon = Icons.Rounded.Menu
    )
}