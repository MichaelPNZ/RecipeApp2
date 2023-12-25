package com.example.recipeapp.ui.recipes.recipe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.model.Recipe

class RecipeViewModel : ViewModel() {

    private val _recipeUIState = MutableLiveData<RecipeUIState>()

    val recipeUIState: MutableLiveData<RecipeUIState>
        get() = _recipeUIState

    data class RecipeUIState(
        var recipe: Recipe? = null,
        val isFavorite: Boolean = false,
        val quantity: Int = 1,
    )

}