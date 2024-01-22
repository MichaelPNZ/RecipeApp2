package com.example.recipeapp.data

import androidx.room.*
import com.example.recipeapp.model.Category

@Dao
interface CategoriesDao {

    @Query("SELECT * FROM category")
    fun getAll(): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(categories: List<Category>)

    @Query("DELETE FROM category")
    fun deleteAll()
}