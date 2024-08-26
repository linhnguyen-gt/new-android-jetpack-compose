package com.example.newandroidjetpackcompose.services.retrofit_services

import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("{endpoint}")
    suspend fun getRequest(
        @Path("endpoint") endpoint: String,
        @QueryMap params: Map<String, String>? = null
    ): Response<Map<String, Any>>

    @POST("{endpoint}")
    suspend fun postRequest(
        @Path("endpoint") endpoint: String,
        @Body body: Any? = null
    ): Response<Map<String, Any>>

    @PUT("{endpoint}")
    suspend fun putRequest(
        @Path("endpoint") endpoint: String,
        @Body body: Any? = null
    ): Response<Map<String, Any>>

    @DELETE("{endpoint}")
    suspend fun deleteRequest(
        @Path("endpoint") endpoint: String,
        @QueryMap params: Map<String, String>? = null
    ): Response<Map<String, Any>>

    @PATCH("{endpoint}")
    suspend fun patchRequest(
        @Path("endpoint") endpoint: String,
        @Body body: Any? = null
    ): Response<Map<String, Any>>
}