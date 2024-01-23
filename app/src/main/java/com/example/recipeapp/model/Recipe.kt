package com.example.recipeapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

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
        return list?.joinToString { "${it.quantity}:${it.unitOfMeasure}:${it.description}" } ?: ""
    }

    @TypeConverter
    fun toIngredientsList(value: String): List<Ingredient> {
        val chunks = value.split(",", ":")
        return chunks.chunked(3) { chunk ->
            if (chunk.size == 3) {
                Ingredient(chunk[0], chunk[1], chunk[2])
            } else {
                null
            }
        }.filterNotNull()
    }

    @TypeConverter
    fun fromMethodList(value: List<String>?): String {
        return value?.joinToString(":") ?: ""
    }

    @TypeConverter
    fun toMethodList(value: String): List<String> {
        return value.split(":")
    }
}