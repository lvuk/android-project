package com.vuk.foodhelper

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.vuk.foodhelper.adapters.FoodAdapter
import com.vuk.foodhelper.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var adapter: FoodAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.fragment = this
        getMealByCategory("Beef")
        binding.FragmentHomeCategories.addOnTabSelectedListener(tabSelectedListener)

        return binding.root
    }

    fun search() {
        lifecycleScope.launch {
            val response = RetrofitInstance.api.getMealByIngredient(binding.FragmentHomeSearchbox.text.toString())
            if(response.isSuccessful) {
                Log.d("HomeFragment", "search: ${response.body()}")
                adapter = response.body()?.let {
                    it.meals?.let { it1 ->
                        FoodAdapter(
                            it1,
                            onClick = { id, view ->
                                val extras = FragmentNavigatorExtras(view to "meal_image")
                                findNavController().navigate(
                                    HomeFragmentDirections.actionHomeFragmentToMealFragment(
                                        id
                                    ),
                                    extras
                                )
                            }
                        )
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_LONG).show()
                adapter = FoodAdapter(
                    emptyList(),
                    onClick = { id, view->
                        val extras = FragmentNavigatorExtras(view to "meal_image")
                        findNavController().navigate(
                            HomeFragmentDirections.actionHomeFragmentToMealFragment(
                                id
                            ),
                            extras
                        )
                    }
                )
            }
            binding.FragmentHomeRecyclerView.adapter = adapter
        }
    }

    fun getMealByCategory(category: String) {
        lifecycleScope.launch {
            val response = RetrofitInstance.api.getMealByCategory(category)
            adapter = if (response.isSuccessful) {
                response.body()?.let {
                    it.meals?.let { it1 ->
                        FoodAdapter(
                            it1,
                            onClick = { id, view ->
                                val extras = FragmentNavigatorExtras(view to "meal_image")
                                findNavController().navigate(
                                    HomeFragmentDirections.actionHomeFragmentToMealFragment(
                                        id
                                    ),
                                    extras
                                )
                            }
                        )
                    }
                }
            } else {
                FoodAdapter(
                    emptyList(),
                    onClick = { id , view ->
                        val extras = FragmentNavigatorExtras(view to "meal_image")
                        findNavController().navigate(
                            HomeFragmentDirections.actionHomeFragmentToMealFragment(id),
                            extras
                            )
                    }
                )
            }
            binding.FragmentHomeRecyclerView.adapter = adapter
        }
    }

    private val tabSelectedListener = object : TabLayout.OnTabSelectedListener {

        override fun onTabSelected(tab: TabLayout.Tab?) {
            when (tab?.position) {
                0 -> {
                    getMealByCategory("Beef")
                }
                1 -> {
                    getMealByCategory("Breakfast")
                }
                2 -> {
                    getMealByCategory("Chicken")
                }
                3 -> {
                    getMealByCategory("Dessert")
                }
                4 -> {
                    getMealByCategory("Goat")
                }
                5 -> {
                    getMealByCategory("Miscellaneous")
                }
                6 -> {
                    getMealByCategory("Pasta")
                }
                7 -> {
                    getMealByCategory("Pork")
                }
                8 -> {
                    getMealByCategory("Seafood")
                }
                9 -> {
                    getMealByCategory("Side")
                }
                10 -> {
                    getMealByCategory("Starter")
                }
                11 -> {
                    getMealByCategory("Vegan")
                }
                12 -> {
                    getMealByCategory("Vegetarian")
                }
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {
        }
    }
}

