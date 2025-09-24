package com.newAndroid.newandroidjetpackcompose.presentation.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newAndroid.newandroidjetpackcompose.data.api.DataApiService
import com.newAndroid.newandroidjetpackcompose.data.remote.model.ResponseDataModel
import com.newAndroid.newandroidjetpackcompose.presentation.constants.Routes
import com.newAndroid.newandroidjetpackcompose.presentation.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PageTwoViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val dataApiService: DataApiService,
) : ViewModel() {
    private val _data = MutableStateFlow<List<ResponseDataModel>?>(null)
    val data: StateFlow<List<ResponseDataModel>?> = _data.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    val userParam = appNavigator.consumeArg(Routes.PAGE_TWO, UserParam::class.java)

    init {
        fetchData()
    }

    fun navigateBack() {
        appNavigator.pop()
    }

    fun fetchData() {
        viewModelScope.launch {
            try {
                val response = dataApiService.fetchPopulationData()
                _data.value = response.data
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
