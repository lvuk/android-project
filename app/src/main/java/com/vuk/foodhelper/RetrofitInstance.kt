package com.vuk.foodhelper

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val client = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }).build()

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit by lazy {
        Retrofit.Builder()
            .client(client)
            .baseUrl("https://www.themealdb.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val api by lazy {
        retrofit.create(ApiCaller::class.java)
    }

}
