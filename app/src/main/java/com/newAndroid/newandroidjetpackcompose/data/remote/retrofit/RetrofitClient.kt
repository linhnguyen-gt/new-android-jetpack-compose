package com.newAndroid.newandroidjetpackcompose.data.remote.retrofit

import android.util.Log
import com.google.gson.Gson
import com.newAndroid.newandroidjetpackcompose.data.local.SessionManager
import com.newAndroid.newandroidjetpackcompose.data.remote.api.ApiService
import com.newAndroid.newandroidjetpackcompose.presentation.navigation.AppNavigator
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitClient {
    private const val BASE_URL = "https://datausa.io/api/"
    private const val TAG = "RetrofitClient"

    private lateinit var sessionManager: SessionManager
    private lateinit var appNavigator: AppNavigator

    fun initialize(sessionManager: SessionManager, appNavigator: AppNavigator) {
        RetrofitClient.sessionManager = sessionManager
        RetrofitClient.appNavigator = appNavigator
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor())
        .addInterceptor(loggingInterceptor())
        .authenticator(tokenAuthenticator())
        .connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS).build()

    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()

    private val apiService: ApiService = retrofit.create(ApiService::class.java)

    /**
     * Interceptor for adding Authorization header to all requests
     */
    private fun authInterceptor() = Interceptor { chain ->
        val originalRequest = chain.request()
        val token = sessionManager.getToken()
        val newRequest =
            originalRequest.newBuilder().header("Authorization", "Bearer $token").build()
        chain.proceed(newRequest)
    }


    /**
     * Authenticator for handling 401 Unauthorized errors and refreshing tokens.
     */
    private fun tokenAuthenticator() = object : Authenticator {
        override fun authenticate(route: Route?, response: Response): Request? {
            // Check if we have already tried to authenticate
            if (response.code == 401) {
                // Synchronized block to prevent multiple refresh attempts
                synchronized(this) {
                    val responseBody = response.body.string()

                    val isUnauthorizedMessage = responseBody.let {
                        // Parse the JSON response body to check for the 'message' field
                        val json = Gson().fromJson(it, Map::class.java)
                        (json["message"] as? String) == "Unauthorized"
                    }

                    if (isUnauthorizedMessage) {
                        // Navigate back to the login screen and clear backstack
                        sessionManager.clearSession()
                        appNavigator.navigateToLogin()

                        return null
                    }
                }


            }
            return null

        }
    }

    /**
     * Interceptor for logging request and response data
     */
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


    /**
     * Refreshes the authentication token by making a network request.
     */
    private fun refreshToken(): String? {
        return try {
            // handle token here!!
//            val response = apiService.refreshToken().execute() // Refresh token synchronously
//            if (response.isSuccessful) {
//                val newToken = response.body()?.token
//                if (newToken != null) {
//                    sessionManager.saveToken(newToken) // Save new token
//                    return newToken
//                }
//            }
            null // Return null if refresh failed
        } catch (e: Exception) {
            Log.e(TAG, "Error refreshing token", e)
            null
        }
    }


    /**
     * Request method for performing network calls
     */
    suspend fun request(
        endpoint: String, config: RetrofitClientConfig
    ): BaseResponse<Map<String, Any>> {
        return try {
            val response = when (config.method) {
                RetrofitMethod.GET -> apiService.get(endpoint, config.params)
                RetrofitMethod.POST -> apiService.post(endpoint, config.body)
                RetrofitMethod.PUT -> apiService.put(endpoint, config.body)
                RetrofitMethod.DELETE -> apiService.delete(endpoint)
            }

            val responseBody = response.body()?.string()
            Log.d(TAG, "Raw Response Body: $responseBody")

            val parsedData = if (responseBody != null) {
                parseJsonToMap(responseBody)
            } else {
                emptyMap()
            }

            Log.d(TAG, "Response Data: $parsedData")

            BaseResponse(
                ok = response.isSuccessful, data = parsedData, statusCode = response.code()
            )
        } catch (e: Exception) {
            Log.e(TAG, "Request error", e)
            BaseResponse(
                ok = false, data = emptyMap(), statusCode = 500
            )
        }
    }

    /**
     * Parses a JSON string into a Map
     */
    private fun parseJsonToMap(json: String): Map<String, Any> {
        return try {
            val jsonObject = JSONObject(json)
            val result = mutableMapOf<String, Any>()
            jsonObject.keys().forEach { key ->
                when (val value = jsonObject.get(key)) {
                    is JSONObject -> result[key] = parseJsonObjectToMap(value)
                    is JSONArray -> result[key] = parseJsonArrayToList(value)
                    else -> result[key] = value
                }
            }
            result
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing JSON", e)
            emptyMap()
        }
    }

    /**
     * Parses a JSONObject into a Map
     */
    private fun parseJsonObjectToMap(jsonObject: JSONObject): Map<String, Any> {
        val result = mutableMapOf<String, Any>()
        jsonObject.keys().forEach { key ->
            when (val value = jsonObject.get(key)) {
                is JSONObject -> result[key] = parseJsonObjectToMap(value)
                is JSONArray -> result[key] = parseJsonArrayToList(value)
                else -> result[key] = value
            }
        }
        return result
    }

    /**
     * Parses a JSONArray into a List
     */
    private fun parseJsonArrayToList(jsonArray: JSONArray): List<Any> {
        return (0 until jsonArray.length()).map { i ->
            when (val value = jsonArray.get(i)) {
                is JSONObject -> parseJsonObjectToMap(value)
                is JSONArray -> parseJsonArrayToList(value)
                else -> value
            }
        }
    }

    fun getInstance(): RetrofitClient = this
}


enum class RetrofitMethod { GET, POST, PUT, DELETE }


data class BaseResponse<T>(
    val ok: Boolean, val data: T, val statusCode: Int? = null
)

