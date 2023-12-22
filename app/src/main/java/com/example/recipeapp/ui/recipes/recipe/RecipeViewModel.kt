package com.example.recipeapp.ui.recipes.recipe

import androidx.lifecycle.ViewModel
import com.example.recipeapp.R
import com.example.recipeapp.model.Ingredient

class RecipeViewModel : ViewModel() {

    data class RecipeUIState(
        var recipeId: String? = null,
        var recipeTitle: String? = null,
        var recipeImageUrl: String? = null,
        var ingredients: List<Ingredient>? = null,
        var method: List<String>? = null,
        val isHeart: Int = R.drawable.ic_heart,
        val isHeartEmpty: Int = R.drawable.ic_heart_empty,
    )

}