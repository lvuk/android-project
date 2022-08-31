package com.vuk.foodhelper.models

data class Meals(
    val meals: List<Meal>?
)

data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)

