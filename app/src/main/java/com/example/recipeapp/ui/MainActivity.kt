package com.example.recipeapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCategory.setOnClickListener {
            val navController: NavController = findNavController(R.id.nav_host_fragment)
            navController.navigate(R.id.categoriesListFragment)
        }

        binding.btnFavorite.setOnClickListener {
            val navController: NavController = findNavController(R.id.nav_host_fragment)
            navController.navigate(R.id.favoritesFragment)
        }

    }
}