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
            val url = URL("https://recipes.androidsprint.ru/api/category")
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()

            Log.i("!!!", "Body: ${connection.inputStream.bufferedReader().readText()}")
            Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
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

        val responseString = """
        [
            {
                "id": 0,
                "title": "Бургеры",
                "description": "Рецепты всех популярных видов бургеров",
                "imageUrl": "burger.png"
            },
            {
                "id": 1,
                "title": "Десерты",
                "description": "Самые вкусные рецепты десертов специально для вас",
                "imageUrl": "dessert.png"
            },
            {
                "id": 2,
                "title": "Пицца",
                "description": "Пицца на любой вкус и цвет. Лучшая подборка для тебя",
                "imageUrl": "pizza.png"
            },
            {
                "id": 3,
                "title": "Рыба",
                "description": "Печеная, жареная, сушеная, любая рыба на твой вкус",
                "imageUrl": "fish.png"
            },
            {
                "id": 4,
                "title": "Супы",
                "description": "От классики до экзотики: мир в одной тарелке",
                "imageUrl": "soup.png"
            },
            {
                "id": 5,
                "title": "Салаты",
                "description": "Хрустящий калейдоскоп под соусом вдохновения",
                "imageUrl": "salad.png"
            }
        ]
    """.trimIndent()

        val category = Json.decodeFromString<List<Category>>(responseString)
        println(category)
    }
}