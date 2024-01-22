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
            val currentCategory = recipesRepository.getCategoryById(categoryId.toString())
            val categoryImageLink = "${BASE_URL}images/${currentCategory?.imageUrl}"
            val recipeListCache = recipesRepository.getRecipesFromCache()
            updateUIState(currentCategory?.title, categoryImageLink, recipeListCache)

            val recipesList = recipesRepository.getRecipesByCategoryId(categoryId)
            updateUIState(currentCategory?.title, categoryImageLink, recipesList)

            if (recipesList != null) {
                recipesRepository.insertRecipesIntoCache(recipesList)
            }
        }
    }

    private fun updateUIState(categoryName: String?, categoryImageLink: String, recipesList: List<Recipe>?) {
        recipesList?.let {
            val updateList = it.map { category ->
                category.copy(imageUrl = "${BASE_URL}images/${category.imageUrl}")
            }
            _recipesListUIState.postValue(RecipesListUIState(
                categoryName = categoryName,
                categoryImageLink = categoryImageLink,
                recipesList = updateList,
            ))
        }
    }

    data class RecipesListUIState(
        val categoryName: String? = null,
        val categoryImageLink: String? = null,
        val recipesList: List<Recipe>? = null,
    )
}