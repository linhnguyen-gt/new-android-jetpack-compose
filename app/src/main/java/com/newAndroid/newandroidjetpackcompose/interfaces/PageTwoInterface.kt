package com.newAndroid.newandroidjetpackcompose.interfaces

import com.newAndroid.newandroidjetpackcompose.models.ResponseDataModel
import kotlinx.coroutines.flow.StateFlow

interface PageTwoInterface {
    val data: StateFlow<List<ResponseDataModel>?>
    val error: StateFlow<String?>

    fun navigateBack()
    fun fetchData()
}
