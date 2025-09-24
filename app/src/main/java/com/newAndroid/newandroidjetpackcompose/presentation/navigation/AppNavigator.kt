package com.newAndroid.newandroidjetpackcompose.presentation.navigation

import android.os.Parcelable
import java.util.concurrent.ConcurrentHashMap
import androidx.navigation.NavController
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNavigator @Inject constructor() {
    private lateinit var navController: NavController
    private val argsStore: MutableMap<String, Parcelable> = ConcurrentHashMap()

    fun initialize(navController: NavController) {
        this.navController = navController
    }

    fun setArg(key: String, value: Any) {
        if (::navController.isInitialized) {
            navController.currentBackStackEntry?.savedStateHandle?.set(key, value)
        }
    }

    fun <T : Parcelable> push(route: String, value: T) {
        if (::navController.isInitialized) {
            argsStore[route] = value
            navController.navigate(route)
        }
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

    fun <T : Parcelable> consumeArg(route: String, clazz: Class<T>): T? {
        val value = argsStore.remove(route) ?: return null
        @Suppress("UNCHECKED_CAST")
        return value as? T
    }

    fun pop() {
        if (::navController.isInitialized) {
            navController.popBackStack()
        }
    }

}
