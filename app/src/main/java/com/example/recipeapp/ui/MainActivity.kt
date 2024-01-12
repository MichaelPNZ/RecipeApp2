package com.example.recipeapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ActivityMainBinding
import com.example.recipeapp.model.Category
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : AppCompatActivity() {

    private val json = Json {
        ignoreUnknownKeys = true
    }
    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("!!!", "Метод onCreate() выполняется на потоке: ${Thread.currentThread().name}")

        val thread = Thread {
            val client = OkHttpClient()
            val request: Request = Request.Builder()
                .url("https://recipes.androidsprint.ru/api/category")
                .build()

            Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")

            client.newCall(request).execute().use { response ->
                val responseText = response.body?.string()
                val category = responseText?.let { json.decodeFromString<List<Category>>(it) }
                Log.i("!!!", "Category: $category")
            }
        }
        thread.start()

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