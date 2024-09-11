package com.newAndroid.newandroidjetpackcompose.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newAndroid.newandroidjetpackcompose.apis.ResponseData
import com.newAndroid.newandroidjetpackcompose.interfaces.PageTwoInterface
import com.newAndroid.newandroidjetpackcompose.models.ResponseDataModel
import com.newAndroid.newandroidjetpackcompose.navigation.AppNavigator
import com.newAndroid.newandroidjetpackcompose.services.retrofit_services.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PageTwoViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
) : ViewModel(), PageTwoInterface {
    private val responseData: ResponseData = ResponseData()

    private val _data = MutableStateFlow<BaseResponse<List<ResponseDataModel>?>?>(null)
    override val data: StateFlow<BaseResponse<List<ResponseDataModel>?>?> = _data.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    override val error: StateFlow<String?> = _error.asStateFlow()

    override
    fun navigateBack() {
        appNavigator.pop()
    }

    override
    fun fetchData() {
        viewModelScope.launch {
            try {
                _data.value = responseData.responseApi()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
