package com.example.digiit

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.digiit.data.UserProvider
import com.example.digiit.navgraphs.RootNavigationGraph
import com.example.digiit.ui.theme.DigiitTheme
import com.example.digiit.utils.MemoryRemember
import com.google.firebase.FirebaseApp


class MainActivity : ComponentActivity() {
    companion object {
        val auth = MemoryRemember { UserProvider(FirebaseApp.getInstance()) }
    }

    private var configChanged = false

    public override fun onCreate(savedInstanceState: Bundle?) {
        setContent {
            DigiitTheme {
                val navController = rememberNavController()
                RootNavigationGraph(navController = navController, auth = auth.value)
            }
        }

        super.onCreate(savedInstanceState)
    }

    public override fun onStart() {
        super.onStart()
    }

    public override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        configChanged = true
        recreate()
    }

    public override fun onDestroy() {
        if (!configChanged) {
            auth.free()
        }
        super.onDestroy()
    }
}