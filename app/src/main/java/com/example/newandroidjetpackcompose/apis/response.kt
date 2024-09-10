package com.example.newandroidjetpackcompose.apis

import android.util.Log
import com.example.newandroidjetpackcompose.models.ResponseDataModel
import com.example.newandroidjetpackcompose.services.retrofit_services.ApiProblem
import com.example.newandroidjetpackcompose.services.retrofit_services.BaseResponse
import com.example.newandroidjetpackcompose.services.retrofit_services.RetrofitClient
import com.example.newandroidjetpackcompose.services.retrofit_services.RetrofitClientConfig

class ResponseData {
    suspend fun responseApi(): BaseResponse<List<ResponseDataModel>?> {
        val params = mapOf(
            "drilldowns" to "State",
            "measures" to "Population",
            "year" to "latest"
        )

        val response =
            RetrofitClient.getInstance().request("data", RetrofitClientConfig.get(params))

        if (!response.ok) {
            ApiProblem.handleApiError(response)
        }

        return BaseResponse(
            ok = true,
            data = ResponseDataModel.parseResponse(response.data),
        )

    }
}