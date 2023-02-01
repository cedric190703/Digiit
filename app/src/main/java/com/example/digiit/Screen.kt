package com.example.digiit

sealed class Screen(val route: String) {
    object Splash: Screen(route = "_screen")
    object Home: Screen(route = "home_screen")
    object Wallet: Screen(route = "wallet_screen")
    object HomeLogin: Screen(route = "home_login_screen")
    object LostPassword: Screen(route = "lost_password_screen")
    object CreatePassword: Screen(route = "create_password_screen")
    object FuncOne: Screen(route = "func_one_screen")
    object FuncTwo: Screen(route = "func_two_screen")
    object FuncThree: Screen(route = "func_three_screen")
    object FuncFour: Screen(route = "func_four_screen")
    object FuncFive: Screen(route = "func_five_screen")
}
