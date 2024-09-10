package com.newAndroid.newandroidjetpackcompose.view_model_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.newAndroid.newandroidjetpackcompose.apis.ResponseData
import com.newAndroid.newandroidjetpackcompose.view_models.ResponseViewModel

class ResponseViewModelFactory(private val responseData: ResponseData) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResponseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ResponseViewModel(responseData) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}