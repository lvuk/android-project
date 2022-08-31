package com.vuk.foodhelper.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vuk.foodhelper.R
import com.vuk.foodhelper.databinding.ItemFoodBinding
import com.vuk.foodhelper.models.Meal

class FoodAdapter(
    private val meals: List<Meal>,
    private val onClick: (String, View) -> Unit
): RecyclerView.Adapter<FoodAdapter.MealViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MealViewHolder(
            ItemFoodBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(meals[position])
    }

    override fun getItemCount(): Int = meals.size

    inner class MealViewHolder(private val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(meal: Meal) {
            Glide.with(binding.root.context).load(meal.strMealThumb).error(R.drawable.ic_launcher_foreground).into(binding.ItemFoodImage)
            binding.ItemFoodName.text = meal.strMeal
            binding.ItemFoodMeal.setOnClickListener {
                onClick(meal.idMeal, binding.ItemFoodImage)
            }
            ViewCompat.setTransitionName(binding.ItemFoodImage, meal.idMeal)
        }
    }
}
