package com.example.newandroidjetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newandroidjetpackcompose.views.page_one.PageOneScreen
import com.example.newandroidjetpackcompose.views.page_two.PageTwoScreen

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "page_one"
    ) {
        composable("page_one") {
            PageOneScreen()
        }
        composable("page_two") {
            PageTwoScreen()
        }
    }
}