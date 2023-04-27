package com.example.digiit.navigation


enum class Routes(val route: String, parent: Routes? = null) {
    HOME   ("home"),
    AUTH   ("auth"),

    TICKETS   ("tickets", HOME),
    WALLETS   ("wallets", HOME),
    DATA      ("data", HOME),
    MENU      ("menu", HOME),

    LOGIN     ("login", AUTH),
    REGISTER  ("register", AUTH),
    FORGET    ("forget", AUTH),
    ANIMATION ("animation", AUTH),
    SPLASH    ("splash", AUTH);

    val path: String = if (parent != null) "${parent.path}.$route" else route
}
