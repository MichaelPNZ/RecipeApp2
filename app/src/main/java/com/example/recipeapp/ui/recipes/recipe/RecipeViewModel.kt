package com.example.recipeapp.ui.recipes.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.model.Recipe

class RecipeViewModel : ViewModel() {

    private val _recipeUIState = MutableLiveData<RecipeUIState>()

    val recipeUIState: LiveData<RecipeUIState>
        get() = _recipeUIState

    init {
        Log.i("!!!", "Temporary block of primary initialization")
        _recipeUIState.value = RecipeUIState(isFavorite = true)
    }

    data class RecipeUIState(
        var recipe: Recipe? = null,
        var isFavorite: Boolean = false,
        val quantity: Int = 1,
    )

}