package com.newAndroid.newandroidjetpackcompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.newAndroid.newandroidjetpackcompose.presentation.constants.Routes
import com.newAndroid.newandroidjetpackcompose.presentation.ui.login.LoginScreen
import com.newAndroid.newandroidjetpackcompose.presentation.ui.pageOne.PageOneScreen
import com.newAndroid.newandroidjetpackcompose.presentation.ui.pageTwo.PageTwoScreen

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            LoginScreen()
        }
        composable(Routes.PAGE_ONE) {
            PageOneScreen()
        }
        composable(Routes.PAGE_TWO) {
            PageTwoScreen()
        }
    }
}
