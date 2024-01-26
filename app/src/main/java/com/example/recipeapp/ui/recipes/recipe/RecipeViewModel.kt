package com.example.recipeapp.ui.recipes.recipe

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.BASE_URL
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
            val recipeCache = recipesRepository.getRecipeByIdFromCache(recipeId)
            updateUIState(recipeCache)
            val recipe = recipesRepository.getRecipeById(recipeId.toString())

            val updatedRecipe = recipe?.copy(
                categoryId = recipeCache.categoryId,
                isFavorite = recipeCache.isFavorite)

            if (updatedRecipe != null) {
                updateUIState(updatedRecipe)
            }
        }
    }

    fun onFavoritesClicked() {
        val currentRecipeUIState = _recipeUIState.value ?: return

        viewModelScope.launch {
            val recipe = currentRecipeUIState.recipe ?: return@launch
            val updateList = recipe.copy(isFavorite = !recipe.isFavorite)
            updateUIState(updateList)
            recipesRepository.insertRecipeIntoCache(updateList)

        }
    }

    fun onChangePortions(progress: Int) {
        val currentRecipeUIState = _recipeUIState.value ?: return
        val updatedRecipeUIState = currentRecipeUIState.copy(
            portionsCount = progress
        )
        _recipeUIState.value = updatedRecipeUIState
    }

    private fun updateUIState(recipe: Recipe) {
        _recipeUIState.postValue(RecipeUIState(
            recipe = recipe,
            portionsCount = _recipeUIState.value?.portionsCount ?: 1,
            recipeImage = "${BASE_URL}images/${recipe.imageUrl}"
        ))
    }

    data class RecipeUIState(
        var recipe: Recipe? = null,
        var portionsCount: Int = 1,
        val recipeImage: String? = null,
    )

}