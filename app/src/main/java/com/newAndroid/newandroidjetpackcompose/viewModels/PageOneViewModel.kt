package com.newAndroid.newandroidjetpackcompose.viewModels

import androidx.lifecycle.ViewModel
import com.newAndroid.newandroidjetpackcompose.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PageOneViewModel @Inject constructor(private val appNavigator: AppNavigator) : ViewModel() {
    fun navigateToPageTwo() {
        appNavigator.push("page_two")
    }
}