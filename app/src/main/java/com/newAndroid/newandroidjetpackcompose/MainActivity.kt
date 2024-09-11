package com.newAndroid.newandroidjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.newAndroid.newandroidjetpackcompose.navigation.AppNavigator
import com.newAndroid.newandroidjetpackcompose.navigation.RootNavGraph
import com.newAndroid.newandroidjetpackcompose.services.retrofit_services.RetrofitClient
import com.newAndroid.newandroidjetpackcompose.services.retrofit_services.SessionManager
import com.newAndroid.newandroidjetpackcompose.ui.theme.NewAndroidJetpackComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var appNavigator: AppNavigator

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        RetrofitClient.initialize(sessionManager, appNavigator)


        setContent {
            NewAndroidJetpackComposeTheme {
                val navController = rememberNavController()
                appNavigator.initialize(navController)
                RootNavGraph(navController)

            }
        }
    }
}