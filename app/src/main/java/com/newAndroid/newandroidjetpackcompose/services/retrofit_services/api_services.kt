package com.newAndroid.newandroidjetpackcompose.services.retrofit_services

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiService {
    @GET
    suspend fun get(@Url url: String, @QueryMap params: Map<String, String>): retrofit2.Response<ResponseBody>

    @POST
    suspend fun post(@Url url: String, @Body body: RequestBody?): retrofit2.Response<ResponseBody>

    @PUT
    suspend fun put(@Url url: String, @Body body: RequestBody?): retrofit2.Response<ResponseBody>

    @DELETE
    suspend fun delete(@Url url: String): retrofit2.Response<ResponseBody>
}