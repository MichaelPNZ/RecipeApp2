package com.example.recipeapp.data

import android.util.Log
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Call
import retrofit2.Retrofit

class RecipesRepository {

    private val recipeApiService: RecipeApiService by lazy {
        val contentType = "application/json".toMediaType()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://recipes.androidsprint.ru/api/")
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()

        retrofit.create(RecipeApiService::class.java)
    }

    suspend fun getRecipeById(id: String?): Recipe? {
        return withContext(Dispatchers.IO) {
            try {
                val recipe = recipeApiService.getRecipeById(id).execute().body()
                recipe
            } catch (e: Exception) {
                Log.e("!!!", "Error fetching categories", e)
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
                Log.e("!!!", "Error fetching categories", e)
                null
            }
        }
    }

    fun getCategoryById(id: String?): Call<Category> {
        return recipeApiService.getCategoryById(id)
    }

    suspend fun getRecipesByCategoryId(groupId: Int): List<Recipe>? {
        return withContext(Dispatchers.IO) {
            try {
                val recipes = recipeApiService.getRecipesByCategoryId(groupId).execute().body()
                recipes
            } catch (e: Exception) {
                Log.e("!!!", "Error fetching categories", e)
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