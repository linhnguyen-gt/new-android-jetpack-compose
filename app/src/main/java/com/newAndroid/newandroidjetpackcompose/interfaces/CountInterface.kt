package com.newAndroid.newandroidjetpackcompose.interfaces

import kotlinx.coroutines.flow.StateFlow

interface CountInterface {
    val data: StateFlow<Int?>

    fun increment()
    fun decrement()
}