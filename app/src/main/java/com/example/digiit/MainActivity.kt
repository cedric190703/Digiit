package com.example.digiit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.digiit.data.UserProvider
import com.example.digiit.navgraphs.RootNavigationGraph
import com.example.digiit.ui.theme.DigiitTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    var a = "vf"
    lateinit var auth: UserProvider
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = UserProvider(FirebaseApp.getInstance())
        db = Firebase.firestore

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

        if (auth.user != null) {
            // User is signed in
            println("\n User is signed in + ${auth.user!!.name}")
        } else {
            // No user is signed in
            println("\n No user is signed in")
        }
    }
}