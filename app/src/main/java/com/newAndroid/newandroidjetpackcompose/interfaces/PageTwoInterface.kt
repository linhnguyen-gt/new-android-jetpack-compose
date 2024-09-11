package com.newAndroid.newandroidjetpackcompose.interfaces

import com.newAndroid.newandroidjetpackcompose.models.ResponseDataModel
import com.newAndroid.newandroidjetpackcompose.services.retrofit_services.BaseResponse
import kotlinx.coroutines.flow.StateFlow

interface PageTwoInterface {
    val data: StateFlow<BaseResponse<List<ResponseDataModel>?>?>
    val error: StateFlow<String?>

    fun navigateBack()
    fun fetchData()
}