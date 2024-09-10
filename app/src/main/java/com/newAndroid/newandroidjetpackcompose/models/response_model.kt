package com.newAndroid.newandroidjetpackcompose.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class ResponseDataModel(
    @SerializedName("ID State") val idState: String? = null,
    @SerializedName("State") val state: String? = null,
    @SerializedName("ID Year") val idYear: Int? = null,
    @SerializedName("Year") val year: String? = null,
    @SerializedName("Population") val population: Int? = null,
    @SerializedName("Slug State") val slugState: String? = null
) {
    companion object {
        fun parseResponse(responseData: Map<String, Any>): List<ResponseDataModel> {
            val dataList = responseData["data"] as? List<*> ?: return emptyList()
            val gson = Gson()
            return dataList.mapNotNull { item ->
                try {
                    gson.fromJson(gson.toJson(item), ResponseDataModel::class.java)
                } catch (e: Exception) {
                    null
                }
            }
        }
    }
}