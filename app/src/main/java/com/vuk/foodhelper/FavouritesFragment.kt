package com.vuk.foodhelper

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.vuk.foodhelper.adapters.FoodAdapter
import com.vuk.foodhelper.databinding.FragmentFavouritesBinding
import com.vuk.foodhelper.models.Meal
import com.vuk.foodhelper.models.MealDetails
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavouritesFragment : Fragment() {

    private lateinit var binding: FragmentFavouritesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)

        getFavourites()

        return binding.root
    }

    private fun getFavourites() {
        val sharedPref = requireContext().getSharedPreferences("favourites", Context.MODE_PRIVATE)
        val favourites = sharedPref.getStringSet("favourites", emptySet())

        val meals = mutableListOf<MealDetails>()
        lifecycleScope.launch {
            favourites?.forEachIndexed { i, id ->
                val meal = getMealDetails(id)
                meals.add(meal)
                if (i == favourites.size - 1) {
                    if (meals.isNotEmpty()) {
                        binding.FragmentFavouritesRV.adapter = FoodAdapter(
                            meals.map {
                                Meal(
                                    idMeal = it.meals?.first()?.idMeal ?: "",
                                    strMeal = it.meals?.first()?.strMeal ?: "",
                                    strMealThumb = it.meals?.first()?.strMealThumb ?: ""
                                )
                            },
                            onClick = { _, _ -> }
                        )
                    }
                }
            }
        }
    }

    suspend fun getMealDetails(mealId: String): MealDetails = lifecycleScope.async {
        val response = RetrofitInstance.api.getMealById(mealId)
        if (response.isSuccessful) {
            return@async response.body() ?: MealDetails(emptyList())
        } else {
            Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_LONG).show()
            return@async MealDetails(emptyList())
        }
    }.await()
}
