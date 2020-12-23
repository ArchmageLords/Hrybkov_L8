package com.example.retrofit

import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.google.gson.annotations.SerializedName

fun main() {
    val request = Retrofit.Builder()
            .baseUrl("https://cat-fact.herokuapp.com/facts/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val gson = Gson()

    val serviceСats = request.create(GetCats::class.java)
    val typeList = object: TypeToken<List<CatResponse?>?>(){}.type

    val catsJson = serviceСats.getCatsJson().execute().body()
    val result = gson.fromJson<List<CatResponse>>(catsJson, typeList)

    println(catsJson)
    println(result)
}

interface GetCats {
    @GET("https://cat-fact.herokuapp.com/facts/")
    fun getCatsJson(): Call<JsonElement>
}

data class CatResponse(
        @SerializedName("status")
        val status: Status,
        @SerializedName("type")
        val type: String,
        @SerializedName("deleted")
        val deleted: Boolean,
        @SerializedName("_id")
        val _id: String,
        @SerializedName("user")
        val user: String,
        @SerializedName("text")
        val text: String,

        //val __v: Int,
        //val source: String,
        //val updatedAt: Timestamp,
        //val createdAt: Timestamp,
        //val used: Boolean
)

data class Status(
        val verified: Boolean,
        val sentCount: Int,
        val feedback: String
)