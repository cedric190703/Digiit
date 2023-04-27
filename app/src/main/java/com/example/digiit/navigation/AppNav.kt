package com.example.digiit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController


class AppNav(var controller: NavController? = null) {
    private val children = HashMap<String, AppNav>()

    fun navigate(path: String) {
        var p = path.indexOf('.')
        if (p == -1) p = path.length
        val head = path.substring(0, p)
        val remain = if(p == path.length) "" else path.substring(p + 1)
        controller!!.navigate(head)
        if (remain.isNotEmpty()) {
            getChild(head)!!.navigate(remain)
        }
    }

    fun registerChild(route: String, nav: NavController) {
        if (children.containsKey(route)) return
        children[route] = AppNav(nav)
    }

    fun getChild(route: String): AppNav? {
        return children[route]
    }

    fun currentPath(): String {
        if (controller!!.currentDestination == null) return ""
        var r = controller!!.currentDestination?.route.orEmpty()
        if (children.containsKey(r)) {
            r += '.' + children[r]!!.currentPath()
        }
        return r
    }

    fun currentRoute(): String {
        if (controller!!.currentDestination == null) return ""
        return controller!!.currentDestination?.route.orEmpty()
    }
}


@Composable
fun AppNavHost(nav: AppNav, route: String, default: String, content: NavGraphBuilder.() -> Unit) {
    val navController = rememberNavController()
    nav.registerChild(route, navController)
    val child = nav.getChild(route)
    val start: String = (child?.currentRoute() ?: "").ifEmpty { default }
    println("======================== Start of '$route' is '$start'")
    NavHost(
        navController = navController,
        route = route,
        startDestination = start
    ) {
        content()
    }
}
