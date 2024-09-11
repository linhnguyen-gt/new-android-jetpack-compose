package com.newAndroid.newandroidjetpackcompose.services.retrofit_services

import android.util.Log
import com.newAndroid.newandroidjetpackcompose.navigation.AppNavigator
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

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor()) // Adds Authorization header with current token
        .addInterceptor(loggingInterceptor()) // Logs request and response data
        .authenticator(tokenAuthenticator()) // Token auto-refresh on 401 Unauthorized
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

    /**
     * Interceptor for adding Authorization header to all requests
     */
    private fun authInterceptor() = Interceptor { chain ->
        val originalRequest = chain.request()
        val token = getToken()
        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
        chain.proceed(newRequest)
    }

//    class TokenAuthenticator @Inject constructor(
//        private val appNavigator: AppNavigator // Inject AppNavigator to handle navigation
//    ) : Authenticator {
//        override fun authenticate(route: Route?, response: Response): Request? {
//            // Check if the response code is 401 (Unauthorized)
//            if (response.code == 401) {
//                synchronized(this) {
//                    // Parse response body to check if message == "Unauthorized"
//                    val responseBody = response.body?.string()
//                    val isUnauthorizedMessage = responseBody?.let {
//                        // Parse the JSON response body to check for the 'message' field
//                        val json = Gson().fromJson(it, Map::class.java)
//                        (json["message"] as? String) == "Unauthorized"
//                    } ?: false
//
//                    if (isUnauthorizedMessage) {
//                        // Navigate back to the login screen and clear backstack
//                        appNavigator.navigateToLogin()
//
//                        // Optionally, you can return null here, or handle a retry with a new token
//                        return null
//                    }
//                }
//            }
//            return null
//        }
//
//        // Optionally, you can add a refreshToken() method if needed to refresh the token
//        private fun refreshToken(): String? {
//            // Logic to refresh the token
//            return null
//        }
//    }

    /**
     * Authenticator for handling 401 Unauthorized errors and refreshing tokens.
     */
    private fun tokenAuthenticator(appNavigator: AppNavigator) = object : Authenticator {
        override fun authenticate(route: Route?, response: Response): Request? {
            // Check if we have already tried to authenticate
            if (response.priorResponse != null && response.priorResponse!!.code == 401) {
                return null // Prevents infinite loop of refreshing
            }

            // Synchronized block to prevent multiple refresh attempts
            synchronized(this) {
                val newToken = refreshToken()

                return if (newToken != null) {
                    Log.d(TAG, "Token refreshed successfully")
                    response.request.newBuilder()
                        .header("Authorization", "Bearer $newToken")
                        .build()
                } else {
                    Log.e(TAG, "Failed to refresh token")
                    null // Refresh failed, return null to prevent retries
                }
            }
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
     * Retrieves the current token from storage (SharedPreferences, database, etc.)
     */
    private fun getToken(): String {
        // Implement token retrieval logic (e.g., from SharedPreferences)
        return "your_token_here"
    }

    /**
     * Refreshes the authentication token by making a network request.
     */
    private fun refreshToken(): String? {
        return try {
//            val response = apiService.refreshToken().execute() // Synchronous token refresh call
//            if (response.isSuccessful) {
//                val newToken = response.body()?.token
//                if (newToken != null) {
//                    saveToken(newToken)
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
     * Saves the new token to storage (e.g., SharedPreferences)
     */
    private fun saveToken(token: String) {
        // Implement logic to save the new token in SharedPreferences or database
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

