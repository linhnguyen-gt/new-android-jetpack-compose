package com.newAndroid.newandroidjetpackcompose.domain.interfaces

import com.newAndroid.newandroidjetpackcompose.data.remote.model.ResponseDataModel
import kotlinx.coroutines.flow.StateFlow

interface PageTwoInterface {
    val data: StateFlow<List<ResponseDataModel>?>
    val error: StateFlow<String?>

    fun navigateBack()
    fun fetchData()
}
