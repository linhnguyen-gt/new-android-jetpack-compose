package com.example.newandroidjetpackcompose.services.retrofit_services

class ApiException(
    override val message: String,
    private val statusCode: Int?
) : Exception() {
    override fun toString(): String = "ApiException: $message (Status code: $statusCode)"
}

object ApiProblem {
    fun <T> handleApiError(response: BaseResponse<T>): Nothing {
        throw when (response.statusCode) {
            400 -> ApiException("Bad request", 400)
            401 -> ApiException("Unauthorized access", 401)
            403 -> ApiException("Forbidden access", 403)
            404 -> ApiException("Resource not found", 404)
            422 -> ApiException("Unprocessable entity", 422)
            500 -> ApiException("Internal server error", 500)
            else -> ApiException("Unknown error", response.statusCode)
        }
    }
}