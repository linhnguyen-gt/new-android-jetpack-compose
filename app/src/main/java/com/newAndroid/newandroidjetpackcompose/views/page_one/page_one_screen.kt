package com.newAndroid.newandroidjetpackcompose.views.page_one


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.newAndroid.newandroidjetpackcompose.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageOneScreen(viewModel: PageOneViewModel = hiltViewModel()) {
    Column {
        Text(text = "Page One")
        Button(onClick = { viewModel.navigateToPageTwo() }) {
            Text("Go to Page Two")
        }
    }
}

@HiltViewModel
class PageOneViewModel @Inject constructor(
    private val appNavigator: AppNavigator
) : ViewModel() {
    fun navigateToPageTwo() {
        appNavigator.push("page_two")
    }
}