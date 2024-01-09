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
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("!!!", "Метод onCreate() выполняется на потоке: ${Thread.currentThread().name}")

        val thread = Thread {
            try {
                val url = URL("https://recipes.androidsprint.ru/api/category")
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()

                Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")

                val responseText = connection.inputStream.bufferedReader().readText()
                val category = Json.decodeFromString<List<Category>>(responseText)
                Log.i("!!!", "Received categories: $category")

                category.forEach { category ->
                    Log.i(
                        "!!!",
                        "CategoryID: ${category.id}," +
                                " title: ${category.title}," +
                                " description: ${category.description}," +
                                " imageUrl: ${category.imageUrl} "
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
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