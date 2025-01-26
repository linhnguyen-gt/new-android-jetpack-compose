package com.newAndroid.newandroidjetpackcompose.data.repository

import com.newAndroid.newandroidjetpackcompose.data.remote.model.ResponseDataModel
import com.newAndroid.newandroidjetpackcompose.data.remote.retrofit.ApiProblem
import com.newAndroid.newandroidjetpackcompose.data.remote.retrofit.BaseResponse
import com.newAndroid.newandroidjetpackcompose.data.remote.retrofit.RetrofitClient
import com.newAndroid.newandroidjetpackcompose.data.remote.retrofit.RetrofitClientConfig

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

        val dataMap: Map<String, Any> = when (val data = response.data["data"]) {
//            is Map<*, *> -> {
//                data.entries
//                    .filter { it.key is String }
//                    .associate { it.key as String to it.value as Any } // Safe cast after filtering
//            }
            is List<*> -> {
                mapOf("data" to data)
            }

            else -> {
                emptyMap()
            }
        }

        return BaseResponse(
            ok = true,
            data = ResponseDataModel.getFromJsonList(dataMap)
        )

    }
}