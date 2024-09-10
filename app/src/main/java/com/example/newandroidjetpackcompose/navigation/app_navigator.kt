package com.example.newandroidjetpackcompose.navigation

import androidx.navigation.NavController
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppNavigator @Inject constructor() {
    private lateinit var navController: NavController

    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    fun push(route: String) {
        if (::navController.isInitialized) {
            navController.navigate(route) {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        } else {
            // Handle uninitialized case - you can log an error, throw an exception, etc.
            throw IllegalStateException("NavController has not been initialized.")
        }
    }

    fun pop(): Boolean {
        return if (::navController.isInitialized) {
            navController.popBackStack()
        } else {
            // Handle uninitialized case
            throw IllegalStateException("NavController has not been initialized.")
        }
    }
}