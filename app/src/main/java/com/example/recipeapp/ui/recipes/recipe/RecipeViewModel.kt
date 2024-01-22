package com.example.recipeapp.ui.recipes.recipe

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

class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    private val appContext = application
    private val recipesRepository = RecipesRepository(appContext)
    private val _recipeUIState = MutableLiveData<RecipeUIState>()
    val recipeUIState: LiveData<RecipeUIState>
        get() = _recipeUIState

    fun loadRecipe(recipeId: Int) {
        viewModelScope.launch {
            val recipe = recipesRepository.getRecipeById(recipeId.toString())
            val drawable = "${BASE_URL}images/${recipe?.imageUrl}"

            _recipeUIState.postValue(RecipeUIState(
                recipe = recipe,
                isFavorite = getFavorites().contains(recipeId.toString()),
                portionsCount = _recipeUIState.value?.portionsCount ?: 1,
                recipeImage = drawable
            ))
        }
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

    fun onChangePortions(progress: Int) {
        val currentRecipeUIState = _recipeUIState.value ?: return
        val updatedRecipeUIState = currentRecipeUIState.copy(
            portionsCount = progress
        )
        _recipeUIState.value = updatedRecipeUIState
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

    data class RecipeUIState(
        var recipe: Recipe? = null,
        var isFavorite: Boolean = false,
        var portionsCount: Int = 1,
        val recipeImage: String? = null,
    )

}