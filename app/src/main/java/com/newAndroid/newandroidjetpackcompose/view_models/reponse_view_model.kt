package com.newAndroid.newandroidjetpackcompose.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newAndroid.newandroidjetpackcompose.apis.ResponseData
import com.newAndroid.newandroidjetpackcompose.models.ResponseDataModel
import com.newAndroid.newandroidjetpackcompose.services.retrofit_services.ApiException
import com.newAndroid.newandroidjetpackcompose.services.retrofit_services.BaseResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ResponseViewModel(private val response: ResponseData) : ViewModel() {
    private val _data = MutableStateFlow<BaseResponse<List<ResponseDataModel>?>?>(null)
    val data: StateFlow<BaseResponse<List<ResponseDataModel>?>?> = _data

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchData() {
        viewModelScope.launch {
            try {
                _data.value = response.responseApi()
            } catch (e: ApiException) {
                _error.value = e.toString()
            } catch (e: Exception) {
                _error.value = "An unexpected error occurred: ${e.message}"
            }
        }
    }

}

