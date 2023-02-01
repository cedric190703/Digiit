package com.example.digiit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.digiit.ui.theme.DigiitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DigiitTheme {
                val navController = rememberNavController()
                SetNavGraph(navController = navController)
            }
        }
    }
}

@Composable
fun test() {
    Text(text = "test2")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    test()
}