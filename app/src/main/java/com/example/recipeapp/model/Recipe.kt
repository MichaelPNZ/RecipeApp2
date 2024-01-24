package com.example.recipeapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
@Entity
data class Recipe(
    @PrimaryKey
    @ColumnInfo(name = "id")val id: Int,
    @ColumnInfo(name = "title")val title: String,
    @ColumnInfo(name = "ingredients")val ingredients: List<Ingredient>,
    @ColumnInfo(name = "method")val method: List<String>,
    @ColumnInfo(name = "imageUrl")val imageUrl: String,
    @ColumnInfo(name = "category_id") @Transient val categoryId: Int? = null,
)

class Converters {
    @TypeConverter
    fun fromIngredientsList(list: List<Ingredient>?): String {
        return Json.encodeToString(list ?: emptyList())
    }

    @TypeConverter
    fun toIngredientsList(value: String): List<Ingredient> {
        return try {
            Json.decodeFromString(value)
        } catch (e: Exception) {
            emptyList()
        }
    }

    @TypeConverter
    fun fromMethodList(list: List<String>?): String {
        return Json.encodeToString(list ?: emptyList())
    }

    @TypeConverter
    fun toMethodList(value: String): List<String> {
        return try {
            Json.decodeFromString(value)
        } catch (e: Exception) {
            emptyList()
        }
    }
}