package com.example.digiit.navgraphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.digiit.navigationlogin.AnimatedSplashScreen
import com.example.digiit.showfeatures.Features
import com.example.digiit.navigationlogin.CreateAccount
import com.example.digiit.navigationlogin.HomeLogin
import com.example.digiit.navigationlogin.LostPassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest


fun NavGraphBuilder.authNavGraph(navController: NavHostController, auth: FirebaseAuth) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Splash.route
    ) {
        composable(route = AuthScreen.Login.route) {
            HomeLogin(
                /*
                Login functions
                */
                getAuth = { ->
                    return@HomeLogin auth
                },
                goToHome = { ->
                    navController.popBackStack()
                    navController.navigate(Graph.HOME)
                },
                SignUpClick = {
                    navController.navigate(AuthScreen.SignUp.route)
                }
            ) {
                navController.navigate(AuthScreen.Forgot.route)
            }
        }
        composable(route = AuthScreen.SignUp.route) {
            CreateAccount (
                onClick = { _mail : String, _password : String , _name : String->
                        println("User does not exist try to create it")
                        //create user with email and password
                        auth.createUserWithEmailAndPassword(_mail, _password).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                println("User is created")
                                navController.popBackStack()
                                navController.navigate(Graph.HOME)
                                auth.currentUser?.sendEmailVerification()
                                auth.signInWithEmailAndPassword(_mail, _password)
                                //set user name
                                auth.currentUser?.updateProfile(
                                    UserProfileChangeRequest.Builder()
                                        .setDisplayName(_name)
                                        .build()
                                )

                            } else {
                                println("User is not created")
                                //print task.exception
                                println(task.exception)
                            }
                    }
                    navController.navigate(AuthScreen.Animation.route)
                },
                onLogin = {
                    navController.navigate(AuthScreen.Login.route)
                }
            )
        }
        composable(route = AuthScreen.Forgot.route) {
            LostPassword(
                goLogin = {
                    navController.navigate(AuthScreen.Login.route)
                },
                sentMail = { _mail : String ->
                    auth.sendPasswordResetEmail(_mail).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            println("Mail sent")
                        } else {
                            println("Mail not sent")
                        }
                    }
                    navController.navigate(AuthScreen.Login.route)
                }
            )
        }
        composable(route = AuthScreen.Animation.route) {
            Features(
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