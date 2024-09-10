package com.example.newandroidjetpackcompose.services.retrofit_services

import okhttp3.RequestBody

data class RetrofitClientConfig(
    val method: RetrofitMethod,
    val params: Map<String, String> = emptyMap(),
    val body: RequestBody? = null
) {

    companion object {
        fun get(params: Map<String, String> = emptyMap()) =
            RetrofitClientConfig(RetrofitMethod.GET, params)

        fun post(body: RequestBody) = RetrofitClientConfig(RetrofitMethod.POST, body = body)
        fun put(body: RequestBody) = RetrofitClientConfig(RetrofitMethod.PUT, body = body)
        fun delete() = RetrofitClientConfig(RetrofitMethod.DELETE)
    }

}
