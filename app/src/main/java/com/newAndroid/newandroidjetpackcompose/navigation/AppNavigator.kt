package com.newAndroid.newandroidjetpackcompose.navigation

import androidx.navigation.NavController
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppNavigator @Inject constructor() {
    private lateinit var navController: NavController

    fun initialize(navController: NavController) {
        this.navController = navController
    }

    fun push(route: String) {
        if (::navController.isInitialized) {
            navController.navigate(route)
        }
    }

    fun replace(route: String) {
        if (::navController.isInitialized) {
            navController.navigate(route) {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        }
    }

    fun pop() {
        if (::navController.isInitialized) {
            navController.popBackStack()
        }
    }

    fun navigateToLogin() {
        if (::navController.isInitialized) {
            navController.navigate("login") {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        }
    }
}