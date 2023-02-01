package com.example.digiit

sealed class Screen(val route: String) {
    object Splash: Screen(route = "_screen")
    object Home: Screen(route = "home_screen")
    object Wallet: Screen(route = "wallet_screen")
    object HomeLogin: Screen(route = "home_login_screen")
    object login_first_page: Screen(route = "login_first_page_screen")
    object login_second_page: Screen(route = "login_first_page_screen")

}
