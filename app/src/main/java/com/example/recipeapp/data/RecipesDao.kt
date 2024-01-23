package com.example.recipeapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipeapp.model.Recipe

@Dao
interface RecipesDao {
    @Query("SELECT * FROM recipe WHERE category_id = :categoryId")
    fun getRecipeByCategoryId(categoryId: Int): List<Recipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipes: List<Recipe>)

    @Query("DELETE FROM recipe")
    fun deleteAll()
}