package com.example.recipeapp.ui.recipes.recipe

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.data.FAVORITES_KEY
import com.example.recipeapp.data.PREF_NAME
import com.example.recipeapp.data.STUB
import com.example.recipeapp.model.Recipe

class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    private val appContext = application
    private val _recipeUIState = MutableLiveData<RecipeUIState>()
    val recipeUIState: LiveData<RecipeUIState>
        get() = _recipeUIState

    data class RecipeUIState(
        var recipe: Recipe? = null,
        var isFavorite: Boolean = false,
        var portionsCount: Int = 1,
    )

    fun loadRecipe(recipeId: Int) {
        // TODO: "load from network"
        val recipe: Recipe = STUB.getRecipeById(recipeId)
        _recipeUIState.value = RecipeUIState(
            recipe = recipe,
            isFavorite = getFavorites().contains(recipeId.toString()),
            portionsCount = _recipeUIState.value?.portionsCount ?: 1
        )
    }

    fun onFavoritesClicked() {
        val currentRecipeUIState = _recipeUIState.value ?: return
        val updatedRecipeUIState = currentRecipeUIState.copy(
            isFavorite = !currentRecipeUIState.isFavorite
        )
        _recipeUIState.value = updatedRecipeUIState

        val allPreferences = getFavorites()
        if (allPreferences.contains(currentRecipeUIState.recipe?.id.toString())) {
            allPreferences.remove(currentRecipeUIState.recipe?.id.toString())
        } else {
            allPreferences.add(currentRecipeUIState.recipe?.id.toString())
        }
        saveFavorites(allPreferences)
    }

    private fun getFavorites(): MutableSet<String> {
        val sharedPrefs = appContext.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        val storedFavorites: Set<String>? = sharedPrefs?.getStringSet(FAVORITES_KEY, null)
        return HashSet(storedFavorites ?: emptySet())
    }

    private fun saveFavorites(favorites: Set<String>) {
        val sharedPrefs = appContext.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        ) ?: return
        with(sharedPrefs.edit()) {
            putStringSet(FAVORITES_KEY, favorites)
            apply()
        }
    }

}