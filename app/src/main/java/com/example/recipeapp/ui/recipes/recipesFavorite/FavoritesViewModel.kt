package com.example.recipeapp.ui.recipes.recipesFavorite

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.data.FAVORITES_KEY
import com.example.recipeapp.data.PREF_NAME
import com.example.recipeapp.data.STUB
import com.example.recipeapp.model.Recipe

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val appContext = application
    private val _favoritesUIState = MutableLiveData<FavoritesUIState>()
    val favoritesUIState: LiveData<FavoritesUIState>
        get() = _favoritesUIState

    fun loadFavoritesRecipes() {
        val allPreferences = getFavorites()
        val allKey = allPreferences.map { it.toInt() }.toSet()
        val filteredRecipes = STUB.getRecipesByIds(allKey)

        _favoritesUIState.value = FavoritesUIState(
            recipe = filteredRecipes,
        )
    }

    private fun getFavorites(): MutableSet<String> {
        val sharedPrefs = appContext.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        val storedFavorites: Set<String>? = sharedPrefs?.getStringSet(FAVORITES_KEY, null)
        return HashSet(storedFavorites ?: emptySet())
    }

    data class FavoritesUIState(
        val recipe: List<Recipe>? = null,
    )
}