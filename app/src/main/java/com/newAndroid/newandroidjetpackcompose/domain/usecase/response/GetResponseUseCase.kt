package com.newAndroid.newandroidjetpackcompose.domain.usecase.response

import com.newAndroid.newandroidjetpackcompose.data.remote.model.ResponseDataModel
import com.newAndroid.newandroidjetpackcompose.data.remote.retrofit.BaseResponse
import com.newAndroid.newandroidjetpackcompose.data.repository.ResponseData

class GetResponseUseCase {
    private val responseData = ResponseData()

    suspend operator fun invoke(): BaseResponse<List<ResponseDataModel>?> {
        return responseData.responseApi()
    }
}