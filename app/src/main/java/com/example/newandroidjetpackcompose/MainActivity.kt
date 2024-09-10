package com.example.newandroidjetpackcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.newandroidjetpackcompose.navigation.AppNavigator
import com.example.newandroidjetpackcompose.navigation.RootNavGraph
import com.example.newandroidjetpackcompose.ui.theme.NewAndroidJetpackComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var appNavigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Log whether appNavigator is injected properly
        if (::appNavigator.isInitialized) {
            Log.d("MainActivity", "AppNavigator has been injected.")
        } else {
            Log.e("MainActivity", "AppNavigator injection failed.")
        }

        setContent {
            NewAndroidJetpackComposeTheme {
                val navController = rememberNavController()
                appNavigator.setNavController(navController)
                RootNavGraph(navController = navController)
            }
        }
    }
}