package com.example.digiit

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.digiit.data.UserProvider
import com.example.digiit.navigation.AppNav
import com.example.digiit.navigation.RootNavigationGraph
import com.example.digiit.ui.theme.DigiitTheme
import com.example.digiit.utils.MemoryRemember
import com.google.firebase.FirebaseApp


class MainActivity : ComponentActivity() {
    companion object {
        val auth = MemoryRemember { UserProvider(FirebaseApp.getInstance()) }
    }

    lateinit var app: ApplicationData

    public override fun onCreate(savedInstanceState: Bundle?) {
        auth.unlock()

        setContent {
            DigiitTheme {
                val navController = rememberNavController()
                val app = ApplicationData(applicationContext, AppNav(), auth.value)
                RootNavigationGraph(app)
            }
        }

        super.onCreate(savedInstanceState)
    }

    public override fun onStart() {
        super.onStart()
    }

    public override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        auth.lock()
        recreate()
    }

    public override fun onDestroy() {
        auth.free()
        super.onDestroy()
    }
}