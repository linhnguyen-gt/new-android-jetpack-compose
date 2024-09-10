package com.example.newandroidjetpackcompose.view_model_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newandroidjetpackcompose.apis.ResponseData
import com.example.newandroidjetpackcompose.view_models.ResponseViewModel

class ResponseViewModelFactory(private val responseData: ResponseData) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResponseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ResponseViewModel(responseData) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}