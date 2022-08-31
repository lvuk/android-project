package com.vuk.foodhelper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationBarView
import com.vuk.foodhelper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.Activity_Main_Fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.ActivityMainBottomNavigation.setOnItemSelectedListener(bottomNavListener)
        binding.ActivityMainBottomNavigation.setupWithNavController(findNavController(R.id.Activity_Main_Fragment))

    }

    private val bottomNavListener =
        NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    navController.navigate(R.id.home)
                    true
                }
                R.id.favourites -> {
                    navController.navigate(R.id.favourites)
                    true
                }
                else -> false
            }
        }

    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()
        return navController.navigateUp()
    }
}
