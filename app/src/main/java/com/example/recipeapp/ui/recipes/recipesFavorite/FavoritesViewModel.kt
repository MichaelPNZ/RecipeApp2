package com.example.recipeapp.ui.recipes.recipesFavorite

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.BASE_URL
import com.example.recipeapp.data.FAVORITES_KEY
import com.example.recipeapp.data.PREF_NAME
import com.example.recipeapp.data.RecipesRepository
import com.example.recipeapp.model.Recipe
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val appContext = application
    private val recipesRepository = RecipesRepository(appContext)
    private val _favoritesUIState = MutableLiveData<FavoritesUIState>()
    val favoritesUIState: LiveData<FavoritesUIState>
        get() = _favoritesUIState

    fun loadFavoritesRecipes() {
        val allPreferences = getFavorites()
        val allKey = allPreferences.map { it.toInt() }.toSet()

        viewModelScope.launch {
            val filteredRecipesCache = recipesRepository.getRecipesByIdsFromCache(allKey)
            updateUIState(filteredRecipesCache)
            val filteredRecipes = recipesRepository.getRecipesByIds(allKey)
            updateUIState(filteredRecipes)
        }
    }

    private fun updateUIState(recipeList: List<Recipe>?) {
        recipeList?.let {
            val updateList = it.map { category ->
                category.copy(imageUrl = "${BASE_URL}images/${category.imageUrl}")
            }
            _favoritesUIState.postValue(FavoritesUIState(
                recipe = updateList,
            ))
        }
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