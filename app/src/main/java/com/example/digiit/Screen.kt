package com.example.digiit

sealed class Screen(val route: String) {
    object Splash: Screen(route = "_screen")
    object Home: Screen(route = "home_screen")
    object Wallet: Screen(route = "wallet_screen")
    object HomeLogin: Screen(route = "home_login_screen")
    object LostPassword: Screen(route = "lost_password_screen")
    object CreateAccount: Screen(route = "create_account_screen")
    object ShowFonctionnalites: Screen(route = "show_fonctionnalites")
}
