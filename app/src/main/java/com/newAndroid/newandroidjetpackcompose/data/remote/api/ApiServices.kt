package com.newAndroid.newandroidjetpackcompose.data.remote.api

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun get(
        @Url url: String,
        @QueryMap params: Map<String, String>
    ): retrofit2.Response<ResponseBody>

    @POST
    suspend fun post(@Url url: String, @Body body: RequestBody?): retrofit2.Response<ResponseBody>

    @PUT
    suspend fun put(@Url url: String, @Body body: RequestBody?): retrofit2.Response<ResponseBody>

    @DELETE
    suspend fun delete(@Url url: String): retrofit2.Response<ResponseBody>
}

