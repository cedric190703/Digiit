package com.example.digiit.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import com.example.digiit.ApplicationData
import com.example.digiit.screens.login.AnimatedSplashScreen
import com.example.digiit.screens.FeaturesScreen
import com.example.digiit.screens.login.CreateAccount
import com.example.digiit.screens.login.HomeLogin
import com.example.digiit.screens.login.LostPassword
import es.dmoral.toasty.Toasty


@Composable
fun AuthNavGraph(auth: ApplicationData) {
    AppNavHost(nav = auth.navigation, route = Routes.AUTH.route, default = Routes.SPLASH.route) {
        composable(route = Routes.LOGIN.route) {
            HomeLogin(
                auth = auth,
                onClickGoHome = {
                    auth.navigation.navigate(Routes.HOME.path)
                },
                onClickSignUp = {
                    auth.navigation.navigate(Routes.REGISTER.path)
                },
                onClickForget = {
                    auth.navigation.navigate(Routes.FORGET.path)
                }
            )
        }
        composable(route = Routes.REGISTER.route) {
            CreateAccount(
                onClickCreate = { _mail: String, _password: String, _ ->
                    println("User does not exist try to create it")
                    //create user with email and password

                    auth.userProvider.registerRemoteUser(_mail, _password) { error, _ ->
                        if (error == null) {
                            /*auth.user?.sendEmailVerification()
                            set user name
                            auth.currentUser?.updateProfile(
                                UserProfileChangeRequest.Builder()
                                    .setDisplayName(_name)
                                    .build()
                            )*/
                            Toasty.success(
                                auth.context,
                                "Enregisrement réussie",
                                Toast.LENGTH_SHORT,
                                true
                            ).show()
                            auth.navigation.navigate(Routes.ANIMATION.path)
                        } else {
                            Toasty.error(
                                auth.context,
                                "Enregistremet échouée",
                                Toast.LENGTH_SHORT,
                                true
                            ).show()
                        }
                    }
                    auth.navigation.navigate(Routes.ANIMATION.path)
                },
                onClickLogin = {
                    auth.navigation.navigate(Routes.LOGIN.path)
                }
            )
        }
        composable(route = Routes.FORGET.route) {
            LostPassword(
                goLogin = {
                    auth.navigation.navigate(Routes.LOGIN.path)
                },
                sentMail = { _mail: String ->
                    auth.userProvider.sendPasswordResetEmail(_mail) { error ->
                        if (error == null) {
                            Toasty.success(auth.context, "Mail envoyé", Toast.LENGTH_SHORT, true)
                                .show()
                        } else {
                            error.printStackTrace()
                            Toasty.error(
                                auth.context,
                                "Echec de l'envoie du mail",
                                Toast.LENGTH_SHORT,
                                true
                            ).show()
                        }
                    }
                    auth.navigation.navigate(Routes.LOGIN.path)
                }
            )
        }
        composable(route = Routes.ANIMATION.route) {
            FeaturesScreen(
                onClick = {
                    auth.navigation.navigate(Routes.HOME.path)
                }
            )
        }
        composable(route = Routes.SPLASH.route) {
            AnimatedSplashScreen(auth)
        }
    }
}
