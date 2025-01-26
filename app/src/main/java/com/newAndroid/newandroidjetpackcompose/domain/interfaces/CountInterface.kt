package com.newAndroid.newandroidjetpackcompose.domain.interfaces

import kotlinx.coroutines.flow.StateFlow

interface CountInterface {
    val data: StateFlow<Int?>

    fun increment()
    fun decrement()
}