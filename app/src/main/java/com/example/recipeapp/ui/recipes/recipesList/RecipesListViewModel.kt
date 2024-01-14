package com.example.recipeapp.ui.recipes.recipesList

import android.app.Application
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipesRepository
import com.example.recipeapp.model.Recipe
import kotlinx.coroutines.launch
import java.io.InputStream

class RecipesListViewModel(application: Application) : AndroidViewModel(application) {

    private val recipesRepository = RecipesRepository()
    private val appContext = application
    private val _recipesListUIState = MutableLiveData<RecipesListUIState>()
    val recipesListUIState: LiveData<RecipesListUIState>
        get() = _recipesListUIState

    fun loadRecipesList(categoryId: Int) {
        viewModelScope.launch {
            val recipesList = recipesRepository.getRecipesByCategoryId(categoryId)
            var categoryDrawable: Drawable? = null
            val currentCategory = recipesRepository.getCategoryById(categoryId.toString())

            try {
                val inputStream: InputStream? = currentCategory?.imageUrl?.let {
                    appContext.assets?.open(it)
                }
                categoryDrawable = Drawable.createFromStream(inputStream, null)
            } catch (ex: Exception) {
                Log.e("mylog", "Error: ${ex.stackTraceToString()}")
            }

            _recipesListUIState.value = RecipesListUIState(
                categoryName = currentCategory?.title,
                categoryImage = categoryDrawable,
                recipesList = recipesList,
            )
        }
    }

    data class RecipesListUIState(
        val categoryName: String? = null,
        val categoryImage: Drawable? = null,
        var recipesList: List<Recipe>? = null,
    )
}