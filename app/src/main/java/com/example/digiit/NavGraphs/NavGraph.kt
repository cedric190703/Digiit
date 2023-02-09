package com.example.digiit.NavGraphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.digiit.AnimatedSplashScreen
import com.example.digiit.ShowFunctionalities.fonctionnalites
import com.example.digiit.navigation.createAccount
import com.example.digiit.navigation.homeLogin
import com.example.digiit.navigation.lostPassword
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

fun NavGraphBuilder.authNavGraph(navController: NavHostController, auth: FirebaseAuth) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Splash.route
    ) {
        composable(route = AuthScreen.Login.route) {
            homeLogin(
                onClick = { _user: String, _password: String ->
                    //print user and password
                    println(_user)
                    println(_password)
                    //sign in with email and password
                    auth.signInWithEmailAndPassword(_user, _password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            println("User is signed in")
                            navController.popBackStack()
                            navController.navigate(Graph.HOME)
                        } else {
                            println("User is not signed in")
                        }
                    }
                    //navController.popBackStack()
                    //navController.navigate(Graph.HOME)
                },
                SignUpClick = {
                    navController.navigate(AuthScreen.SignUp.route)
                },
                ForgotClick = {
                    navController.navigate(AuthScreen.Forgot.route)
                }
            )
        }
        composable(route = AuthScreen.SignUp.route) {
            createAccount (
                onClick = {
                    navController.navigate(AuthScreen.Animation.route)
                },
                onLogin = {
                    navController.navigate(AuthScreen.Login.route)
                }
            )
        }
        composable(route = AuthScreen.Forgot.route) {
            lostPassword(
                onClick = {
                    navController.navigate(AuthScreen.SignUp.route)
                },
                onLogin = {
                    navController.navigate(AuthScreen.Login.route)
                }
            )
        }
        composable(route = AuthScreen.Animation.route) {
            fonctionnalites(
                onClick = {
                    navController.navigate(AuthScreen.Login.route)
                }
            )
        }
        composable(route = AuthScreen.Splash.route) {
            AnimatedSplashScreen(navController)
        }
    }
}

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen(route = "LOGIN")
    object SignUp : AuthScreen(route = "SIGN_UP")
    object Forgot : AuthScreen(route = "FORGOT")
    object Animation : AuthScreen(route = "ANIMATION")
    object Splash : AuthScreen(route = "SPLASH")
}