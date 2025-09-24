package com.newAndroid.newandroidjetpackcompose.presentation.ui.viewModels

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import com.newAndroid.newandroidjetpackcompose.presentation.constants.Routes
import com.newAndroid.newandroidjetpackcompose.presentation.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@HiltViewModel
class PageOneViewModel @Inject constructor(private val appNavigator: AppNavigator) : ViewModel() {
    fun navigateToPageTwo() {
        appNavigator.push(Routes.PAGE_TWO, UserParam(id = "1", name = "test pram"))
    }

    fun logout() {
        appNavigator.replace(Routes.LOGIN)
    }
}

@Parcelize
data class UserParam(val id: String, val name: String) : Parcelable
