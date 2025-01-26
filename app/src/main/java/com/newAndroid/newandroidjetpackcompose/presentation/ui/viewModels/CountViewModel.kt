package com.newAndroid.newandroidjetpackcompose.presentation.ui.viewModels

import androidx.lifecycle.ViewModel
import com.newAndroid.newandroidjetpackcompose.domain.interfaces.CountInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CountViewModel : ViewModel(), CountInterface {
    private val _data = MutableStateFlow<Int?>(0)
    override val data: StateFlow<Int?> = _data.asStateFlow()

    override fun decrement() {
        _data.value = _data.value!! - 1
    }

    override fun increment() {
        _data.value = _data.value!! + 1
    }
}
