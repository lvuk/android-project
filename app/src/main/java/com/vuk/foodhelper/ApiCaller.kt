package com.vuk.foodhelper

import com.vuk.foodhelper.models.MealDetails
import com.vuk.foodhelper.models.Meals
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCaller {

    @GET("/api/json/v1/1/filter.php")
    suspend fun getMealByIngredient(
        @Query("i") ingredient: String
    ): Response<Meals>

    @GET("/api/json/v1/1/filter.php")
    suspend fun getMealByCategory(
        @Query("c") category: String
    ): Response<Meals>

    @GET("/api/json/v1/1/lookup.php")
    suspend fun getMealDetails(
        @Query("i") id: String
    ): Response<MealDetails>

    @GET("/api/json/v1/1/lookup.php")
    suspend fun getMealById(
        @Query("i") id: String
    ): Response<MealDetails>
}
