package com.example.digiit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.digiit.NavGraphs.RootNavigationGraph
import com.example.digiit.ui.theme.DigiitTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    var a = "vf"
    var b = a.length
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth

        super.onCreate(savedInstanceState)
        setContent {
            DigiitTheme {
                val navController = rememberNavController()
                RootNavigationGraph(navController = navController, auth = auth)
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser

        if (currentUser != null) {
            // User is signed in
            println("\n User is signed in + ${currentUser.email}")
        } else {
            // No user is signed in
            println("\n No user is signed in")
        }
    }
}