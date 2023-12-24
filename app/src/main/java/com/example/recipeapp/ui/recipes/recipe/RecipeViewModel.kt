package com.example.recipeapp.ui.recipes.recipe

import androidx.lifecycle.ViewModel
import com.example.recipeapp.model.Recipe

class RecipeViewModel : ViewModel() {

    data class RecipeUIState(
        var recipe: Recipe? = null,
        val isFavorite: Boolean = false,
        val quantity: Int = 3,
    )

}