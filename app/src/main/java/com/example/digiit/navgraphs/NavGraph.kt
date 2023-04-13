package com.example.digiit.navgraphs

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.digiit.data.UserProvider
import com.example.digiit.navigationlogin.AnimatedSplashScreen
import com.example.digiit.showfeatures.Features
import com.example.digiit.navigationlogin.CreateAccount
import com.example.digiit.navigationlogin.HomeLogin
import com.example.digiit.navigationlogin.LostPassword
import es.dmoral.toasty.Toasty

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    auth: UserProvider,
    context: Context) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Splash.route
    ) {
        composable(route = AuthScreen.Login.route) {
            HomeLogin(
                /*
                Login functions
                */
                auth = auth,
                onClickGoHome = { ->
                    navController.popBackStack()
                    navController.navigate(Graph.HOME)
                },
                onClickSignUp = {
                    navController.navigate(AuthScreen.SignUp.route)
                },
                onClickForget = {
                    navController.navigate(AuthScreen.Forgot.route)
                }
            )
        }
        composable(route = AuthScreen.SignUp.route) {
            CreateAccount (
                onClickCreate = { _mail : String, _password : String, _name : String->
                        println("User does not exist try to create it")
                        //create user with email and password

                        auth.registerRemoteUser(_mail, _password) { error, _ ->
                            if (error == null) {
                                /*auth.user?.sendEmailVerification()
                                set user name
                                auth.currentUser?.updateProfile(
                                    UserProfileChangeRequest.Builder()
                                        .setDisplayName(_name)
                                        .build()
                                )*/
                                Toasty.success(context, "Enregisrement réussie", Toast.LENGTH_SHORT, true).show()
                                navController.popBackStack()
                                navController.navigate(AuthScreen.Animation.route)
                            } else {
                                Toasty.error(context, "Enregistremet échouée", Toast.LENGTH_SHORT, true).show()
                            }
                    }
                    navController.navigate(AuthScreen.Animation.route)
                },
                onClickLogin = {
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
                    auth.sendPasswordResetEmail(_mail) { error ->
                        if (error == null) {
                            Toasty.success(context, "Mail envoyé", Toast.LENGTH_SHORT, true).show()
                        } else {
                            error.printStackTrace()
                            Toasty.error(context, "Echec de l'envoie du mail", Toast.LENGTH_SHORT, true).show()
                        }
                    }
                    navController.navigate(AuthScreen.Login.route)
                }
            )
        }
        composable(route = AuthScreen.Animation.route) {
            Features(
                onClick = {
                    navController.navigate(Graph.HOME)
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