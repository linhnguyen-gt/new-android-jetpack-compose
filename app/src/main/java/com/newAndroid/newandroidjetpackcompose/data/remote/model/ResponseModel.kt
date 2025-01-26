package com.newAndroid.newandroidjetpackcompose.data.remote.model

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
        private fun fromJson(json: Map<String, Any>): ResponseDataModel {
            return Gson().fromJson(Gson().toJson(json), ResponseDataModel::class.java)
        }

        fun getFromJsonList(json: Map<String, Any>): List<ResponseDataModel> {
            val dataList = mutableListOf<ResponseDataModel>()
            json.forEach { (_, value) ->
                if (value is List<*>) {
                    value.forEach { category ->
                        if (category is Map<*, *>) {
                            @Suppress("UNCHECKED_CAST")
                            val model = fromJson(category as Map<String, Any>)
                            dataList.add(model)
                        }
                    }
                }
            }
            return dataList
        }
    }
}