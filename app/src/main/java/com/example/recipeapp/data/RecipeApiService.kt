package com.example.recipeapp.data

import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RecipeApiService {

    @GET("recipe/{id}")
    fun getRecipeById(@Path("id") user: String?): Call<Recipe>

    @GET("recipes")
    fun getRecipesByIds(@Query("ids") sort: String?): Call<List<Recipe>>

    @GET("category/{id}")
    fun getCategoryById(@Path("id") user: String?): Call<Category>

    @GET("category/{id}/recipes")
    fun getRecipesByCategoryId(@Path("id") groupId: Int): Call<List<Recipe>>

    @GET("category")
    fun getCategories(): Call<List<Category>>
}