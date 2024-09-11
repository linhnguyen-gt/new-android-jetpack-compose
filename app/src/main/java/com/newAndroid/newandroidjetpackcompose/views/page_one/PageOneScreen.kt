package com.newAndroid.newandroidjetpackcompose.views.page_one


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.newAndroid.newandroidjetpackcompose.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PageOneViewModel @Inject constructor(
    private val appNavigator: AppNavigator
) : ViewModel() {
    fun navigateToPageTwo() {
        appNavigator.push("page_two")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PageOneScreen(pageOneViewModel: PageOneViewModel = hiltViewModel()) {
    Scaffold(topBar = {
        TopAppBar(title = { Text("Page One") })
    }) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Button(onClick = { pageOneViewModel.navigateToPageTwo() }) {
                Text("Go to Page Two")
            }
        }
    }
}

