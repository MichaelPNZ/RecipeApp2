package com.example.recipeapp.ui.recipes.recipesList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.BASE_URL
import com.example.recipeapp.data.RecipesRepository
import com.example.recipeapp.model.Recipe
import kotlinx.coroutines.launch

class RecipesListViewModel(application: Application) : AndroidViewModel(application) {

    private val recipesRepository = RecipesRepository(application)
    private val _recipesListUIState = MutableLiveData<RecipesListUIState>()
    val recipesListUIState: LiveData<RecipesListUIState>
        get() = _recipesListUIState

    fun loadRecipesList(categoryId: Int) {
        viewModelScope.launch {
            val recipesList = recipesRepository.getRecipesByCategoryId(categoryId)
            val currentCategory = recipesRepository.getCategoryById(categoryId.toString())
            val categoryImageLink = "${BASE_URL}images/${currentCategory?.imageUrl}"

            recipesList?.forEach {
                it.imageUrl = "${BASE_URL}images/${it.imageUrl}"
            }

            _recipesListUIState.postValue(RecipesListUIState(
                categoryName = currentCategory?.title,
                categoryImageLink = categoryImageLink,
                recipesList = recipesList,
            ))

        }
    }

    data class RecipesListUIState(
        val categoryName: String? = null,
        val categoryImageLink: String? = null,
        val recipesList: List<Recipe>? = null,
    )
}