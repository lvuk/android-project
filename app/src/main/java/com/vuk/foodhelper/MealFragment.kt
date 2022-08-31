package com.vuk.foodhelper

import android.content.Context
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.vuk.foodhelper.databinding.FragmentMealBinding
import com.vuk.foodhelper.models.MealDetails
import kotlinx.coroutines.launch

class MealFragment : Fragment() {

    private lateinit var binding: FragmentMealBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.slide_left,
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealBinding.inflate(inflater, container, false)

        val navArgs by navArgs<MealFragmentArgs>()
        getMealDetails(navArgs.mealId)

        if(isItFavourite(navArgs.mealId)) {
            binding.FragmentMealFavourite.setImageResource(R.drawable.ic_baseline_favorite_24)
        }
        binding.FragmentMealFavourite.setOnClickListener {
            if(isItFavourite(navArgs.mealId)) {
                binding.FragmentMealFavourite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                storeRemoveFromPrefs(isItStoring = false, navArgs.mealId)
            } else {
                binding.FragmentMealFavourite.setImageResource(R.drawable.ic_baseline_favorite_24)
                storeRemoveFromPrefs(isItStoring = true, navArgs.mealId)
            }
        }

        return binding.root
    }

    private fun storeRemoveFromPrefs(isItStoring: Boolean, id: String) {
        val sharedPref = requireContext().getSharedPreferences("favourites", Context.MODE_PRIVATE)
        val favourites = sharedPref.getStringSet("favourites", emptySet())
        if(isItStoring) {
            val set = mutableSetOf<String>()
            set.add(id)
            if (favourites != null) {
                set.addAll(favourites)
            }
            sharedPref.edit().putStringSet("favourites", set).apply()
        } else {
            val set = mutableSetOf<String>()
            if (favourites != null) {
                set.addAll(favourites)
            }
            set.remove(id)
            sharedPref.edit().putStringSet("favourites", set).apply()
        }
    }

    private fun isItFavourite(id: String): Boolean {
        val sharedPref = requireContext().getSharedPreferences("favourites", Context.MODE_PRIVATE)
        val favourites = sharedPref.getStringSet("favourites", emptySet())
        return favourites?.contains(id) == true

    }

    private fun getMealDetails(mealID: String) {
        lifecycleScope.launch {
            val response = RetrofitInstance.api.getMealDetails(mealID)
            if (response.isSuccessful) {
                response.body()?.let { setupUI(it) }
            } else {
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupUI(body: MealDetails) {
        if (body.meals?.isNotEmpty() == true) {
            Glide.with(requireContext()).load(body.meals.first().strMealThumb)
                .error(R.drawable.ic_launcher_foreground).into(binding.FragmentMealImage)
            binding.FragmentMealArea.text = "Area: ${body.meals?.first()?.strArea}"
            binding.FragmentMealName.text = body.meals.first().strMeal
            binding.FragmentMealCategory.text = body.meals.first().strCategory
            binding.FragmentHomeDescription.text = body.meals.first().strInstructions
        }
    }

}
