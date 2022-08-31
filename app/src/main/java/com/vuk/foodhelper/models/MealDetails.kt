package com.vuk.foodhelper.models

data class MealDetails(
    val meals: List<Details>?
)

data class Details(
    val idMeal: String,
    val strArea: String,
    val strCategory: String,
    val strMealThumb: String,
    val strMeal: String,
    val strInstructions: String
)
