package com.newAndroid.newandroidjetpackcompose.presentation.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newAndroid.newandroidjetpackcompose.data.remote.model.ResponseDataModel
import com.newAndroid.newandroidjetpackcompose.domain.interfaces.PageTwoInterface
import com.newAndroid.newandroidjetpackcompose.domain.usecase.response.GetResponseUseCase
import com.newAndroid.newandroidjetpackcompose.presentation.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PageTwoViewModel @Inject constructor(
    private val appNavigator: AppNavigator
) : ViewModel(), PageTwoInterface {
    private val responseData = GetResponseUseCase()

    private val _data = MutableStateFlow<List<ResponseDataModel>?>(null)
    override val data: StateFlow<List<ResponseDataModel>?> = _data.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    override val error: StateFlow<String?> = _error.asStateFlow()

    init {
        fetchData()
    }

    override
    fun navigateBack() {
        appNavigator.pop()
    }

    override
    fun fetchData() {
        viewModelScope.launch {
            try {
                val response = responseData()
                _data.value = response.data
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
