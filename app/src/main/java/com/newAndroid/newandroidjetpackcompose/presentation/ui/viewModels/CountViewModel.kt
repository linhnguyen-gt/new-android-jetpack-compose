package com.newAndroid.newandroidjetpackcompose.presentation.ui.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CountViewModel : ViewModel() {
    private val _data = MutableStateFlow<Int?>(0)
    val data: StateFlow<Int?> = _data.asStateFlow()

    fun decrement() {
        _data.value = _data.value!! - 1
    }

    fun increment() {
        _data.value = _data.value!! + 1
    }
}
