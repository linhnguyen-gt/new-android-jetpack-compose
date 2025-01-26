package com.newAndroid.newandroidjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.newAndroid.newandroidjetpackcompose.data.local.SessionManager
import com.newAndroid.newandroidjetpackcompose.data.remote.retrofit.RetrofitClient
import com.newAndroid.newandroidjetpackcompose.presentation.navigation.AppNavigator
import com.newAndroid.newandroidjetpackcompose.presentation.navigation.RootNavGraph
import com.newAndroid.newandroidjetpackcompose.presentation.ui.theme.NewAndroidJetpackComposeTheme
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
