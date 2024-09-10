package com.newAndroid.newandroidjetpackcompose.services.retrofit_services

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "https://datausa.io/api/"
    private const val TAG = "RetrofitClient"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor())
        .addInterceptor(loggingInterceptor())
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ApiService = retrofit.create(ApiService::class.java)

    private fun authInterceptor() = Interceptor { chain ->
        val originalRequest = chain.request()
        val token = getToken()
        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
        chain.proceed(newRequest)
    }

    private fun loggingInterceptor() = Interceptor { chain ->
        val request = chain.request()
        val response = chain.proceed(request)

        Log.d(TAG, "URL: ${request.url}")
        Log.d(TAG, "Method: ${request.method}")
        Log.d(TAG, "Headers: ${request.headers}")
        Log.d(TAG, "Body: ${request.body}")
        Log.d(TAG, "Response Code: ${response.code}")
        Log.d(TAG, "Response Body: ${response.peekBody(Long.MAX_VALUE).string()}")

        response
    }

    private fun getToken(): String {
        // Implement token retrieval logic
        return "your_token_here"
    }

    suspend fun request(
        endpoint: String,
        config: RetrofitClientConfig
    ): BaseResponse<Map<String, Any>> {
        return try {
            val response = when (config.method) {
                RetrofitMethod.GET -> apiService.get(endpoint, config.params)
                RetrofitMethod.POST -> apiService.post(endpoint, config.body)
                RetrofitMethod.PUT -> apiService.put(endpoint, config.body)
                RetrofitMethod.DELETE -> apiService.delete(endpoint)
            }


            val responseBody = response.body()?.string()
            Log.d("RetrofitClient", "Raw Response Body: $responseBody")

            val parsedData = if (responseBody != null) {
                parseJsonToMap(responseBody)
            } else {
                emptyMap()
            }

            Log.d("RetrofitClient", "Response Data: $parsedData")

            BaseResponse(
                ok = response.isSuccessful,
                data = parsedData,
                statusCode = response.code()
            )
        } catch (e: Exception) {
            BaseResponse(
                ok = false,
                data = emptyMap(),
                statusCode = 500
            )
        }
    }

    private fun parseJsonToMap(json: String): Map<String, Any> {
        return try {
            val jsonObject = JSONObject(json)
            val dataArray = jsonObject.getJSONArray("data")
            val dataList = mutableListOf<Map<String, Any>>()

            for (i in 0 until dataArray.length()) {
                val stateObject = dataArray.getJSONObject(i)
                val stateMap = mutableMapOf<String, Any>()
                stateObject.keys().forEach { key ->
                    stateMap[key] = stateObject.get(key)
                }
                dataList.add(stateMap)
            }

            mapOf("data" to dataList)
        } catch (e: Exception) {
            Log.e("RetrofitClient", "Error parsing JSON", e)
            Log.e("RetrofitClient", "JSON string: $json")
            emptyMap()
        }
    }

    fun getInstance(): RetrofitClient = this
}

enum class RetrofitMethod { GET, POST, PUT, DELETE }


data class BaseResponse<T>(
    val ok: Boolean,
    val data: T,
    val statusCode: Int? = null
)

