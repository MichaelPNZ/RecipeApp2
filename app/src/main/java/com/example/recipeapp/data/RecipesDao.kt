package com.example.recipeapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipeapp.model.Recipe

@Dao
interface RecipesDao {
    @Query("SELECT * FROM recipe WHERE category_id = :categoryId")
    fun getRecipesByCategoryId(categoryId: Int): List<Recipe>

    @Query("SELECT * FROM recipe WHERE id = :recipeId")
    fun getRecipeById(recipeId: Int): Recipe

    @Query("SELECT * FROM recipe WHERE isFavorite = 1")
    fun getRecipeByFavorite(): List<Recipe>

    @Query("SELECT * FROM recipe WHERE id IN (:recipeId)")
    fun getRecipesByIds(recipeId: Set<Int>): List<Recipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipeList(recipes: List<Recipe>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipe: Recipe)

    @Query("DELETE FROM recipe")
    fun deleteAll()
}