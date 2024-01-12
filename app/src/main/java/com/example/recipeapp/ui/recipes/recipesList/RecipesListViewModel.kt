package com.example.recipeapp.ui.recipes.recipesList

import android.app.Application
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipesRepository
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import kotlinx.coroutines.launch
import java.io.InputStream

class RecipesListViewModel(application: Application) : AndroidViewModel(application) {

    private val recipesRepository = RecipesRepository()
    private val appContext = application
    private val _recipesListUIState = MutableLiveData<RecipesListUIState>()
    val recipesListUIState: LiveData<RecipesListUIState>
        get() = _recipesListUIState

    fun loadRecipesList(category: Category) {
        viewModelScope.launch {
            val recipesList = recipesRepository.getRecipesByCategoryId(category.id)
            var categoryDrawable: Drawable? = null

            try {
                val inputStream: InputStream? = appContext.assets?.open(category.imageUrl)
                categoryDrawable = Drawable.createFromStream(inputStream, null)
            } catch (ex: Exception) {
                Log.e("mylog", "Error: ${ex.stackTraceToString()}")
            }

            if (categoryDrawable != null) {
                _recipesListUIState.value = RecipesListUIState(
                    categoryName = category.title,
                    categoryImage = categoryDrawable,
                    recipesList = recipesList,
                )
            }
        }
    }

    data class RecipesListUIState(
        val categoryName: String? = null,
        val categoryImage: Drawable? = null,
        var recipesList: List<Recipe>? = null,
    )
}