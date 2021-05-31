package com.gmail.hitoridevelop.nothingtodo.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


private const val BASE_URL =
    "https://www.boredapi.com/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

//Can get type of activity, number of participants needed for said activity
//a random activity, price of said activity, and accessibility of the activity
interface BoredApiService {
    @GET("activity")
    suspend fun getRandomActivity(): BoredApiResponse

    @GET("activity?type={type}")
    suspend fun getType(@Path("type") type: String): BoredApiResponse
}

object BoredApi {
    val retrofitService : BoredApiService by lazy {
        retrofit.create(BoredApiService::class.java)
    }
}