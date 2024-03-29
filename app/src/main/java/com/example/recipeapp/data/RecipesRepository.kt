package com.example.recipeapp.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.CoroutineDispatcher
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

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
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

    suspend fun getRecipeByFavorite(): List<Recipe> {
        return withContext(ioDispatcher) {
            recipesDao.getRecipeByFavorite()
        }
    }

    suspend fun getRecipeByIdFromCache(recipeId: Int): Recipe {
        return withContext(ioDispatcher) {
            recipesDao.getRecipeById(recipeId)
        }
    }

    suspend fun insertRecipesListIntoCache(recipes: List<Recipe>) {
        withContext(ioDispatcher) {
            recipesDao.insertRecipeList(recipes)
        }
    }

    suspend fun getRecipesByCategoryIdFromCache(categoryId: Int): List<Recipe> {
        return withContext(ioDispatcher) {
            recipesDao.getRecipesByCategoryId(categoryId)
        }
    }

    suspend fun deleteAllRecipesListFromCache() {
        withContext(ioDispatcher) {
            recipesDao.deleteAll()
        }
    }

    suspend fun insertCategoriesIntoCache(categories: List<Category>) {
        withContext(ioDispatcher) {
            categoriesDao.insert(categories)
        }
    }

    suspend fun insertRecipeIntoCache(recipe: Recipe) {
        withContext(ioDispatcher) {
            recipesDao.insertRecipe(recipe)
        }
    }

    suspend fun getCategoriesFromCache(): List<Category> {
        return withContext(ioDispatcher) {
            categoriesDao.getAll()
        }
    }

    suspend fun deleteAllCategoriesFromCache() {
        withContext(ioDispatcher) {
            categoriesDao.deleteAll()
        }
    }

    suspend fun getRecipeById(id: String?): Recipe? {
        return withContext(ioDispatcher) {
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
        return withContext(ioDispatcher) {
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
        return withContext(ioDispatcher) {
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
        return withContext(ioDispatcher) {
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
        return withContext(ioDispatcher) {
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