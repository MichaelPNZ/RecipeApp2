package com.example.recipeapp.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class RecipesRepository(context: Context) {

    private val db: AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "database-categories"
    ).build()

    private val categoriesDao = db.categoriesDao()
    private val recipesDao = db.recipesDao()

    private val recipeApiService: RecipeApiService by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val contentType = "application/json".toMediaType()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .client(client)
            .build()

        retrofit.create(RecipeApiService::class.java)
    }

    suspend fun insertRecipesIntoCache(recipes: List<Recipe>) {
        withContext(Dispatchers.IO) {
            recipesDao.insert(recipes)
        }
    }

    suspend fun getRecipesFromCache(categoryId: Int): List<Recipe> {
        return withContext(Dispatchers.IO) {
            recipesDao.getRecipeByCategoryId(categoryId)
        }
    }

    suspend fun deleteAllRecipesListFromCache() {
        withContext(Dispatchers.IO) {
            recipesDao.deleteAll()
        }
    }

    suspend fun insertCategoriesIntoCache(categories: List<Category>) {
        withContext(Dispatchers.IO) {
            categoriesDao.insert(categories)
        }
    }

    suspend fun getCategoriesFromCache(): List<Category> {
        return withContext(Dispatchers.IO) {
            categoriesDao.getAll()
        }
    }

    suspend fun deleteAllCategoriesFromCache() {
        withContext(Dispatchers.IO) {
            categoriesDao.deleteAll()
        }
    }

    suspend fun getRecipeById(id: String?): Recipe? {
        return withContext(Dispatchers.IO) {
            try {
                val recipe = recipeApiService.getRecipeById(id).execute().body()
                recipe
            } catch (e: Exception) {
                Log.e("!!!", "Error fetching recipeById", e)
                null
            }
        }
    }

    suspend fun getRecipesByIds(set: Set<Int>): List<Recipe>? {
        return withContext(Dispatchers.IO) {
            try {
                val sortToString = set.joinToString(separator = ",")
                val recipes = recipeApiService.getRecipesByIds(sortToString).execute().body()
                recipes
            } catch (e: Exception) {
                Log.e("!!!", "Error fetching recipesByIds", e)
                null
            }
        }
    }

    suspend fun getCategoryById(id: String?): Category? {
        return withContext(Dispatchers.IO) {
            try {
                val category = recipeApiService.getCategoryById(id).execute().body()
                category
            } catch (e: Exception) {
                Log.e("!!!", "Error fetching categoryById", e)
                null
            }
        }
    }

    suspend fun getRecipesByCategoryId(groupId: Int): List<Recipe>? {
        return withContext(Dispatchers.IO) {
            try {
                val recipes = recipeApiService.getRecipesByCategoryId(groupId).execute().body()
                recipes
            } catch (e: Exception) {
                Log.e("!!!", "Error fetching recipesByCategoryId", e)
                null
            }
        }
    }

    suspend fun getCategories(): List<Category>? {
        return withContext(Dispatchers.IO) {
            try {
                val categories = recipeApiService.getCategories().execute().body()
                categories
            } catch (e: Exception) {
                Log.e("!!!", "Error fetching categories", e)
                null
            }
        }
    }

}