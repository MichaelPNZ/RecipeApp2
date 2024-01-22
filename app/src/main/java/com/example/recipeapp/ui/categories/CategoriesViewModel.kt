package com.example.recipeapp.ui.categories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.BASE_URL
import com.example.recipeapp.data.RecipesRepository
import com.example.recipeapp.model.Category
import kotlinx.coroutines.launch

class CategoriesViewModel(application: Application) : AndroidViewModel(application) {

    private val appContext = application
    private val recipesRepository = RecipesRepository(appContext)
    private val _categoriesUIState = MutableLiveData<CategoriesUIState>()
    val categoriesUIState: LiveData<CategoriesUIState>
        get() = _categoriesUIState

    fun loadCategories() {
        viewModelScope.launch {
            val categoryListCache = recipesRepository.getCategoriesFromCache()
            categoryListCache.let {
                val updateList = it.map { category ->
                    category.copy(imageUrl = "${BASE_URL}images/${category.imageUrl}")
                }
                _categoriesUIState.value = CategoriesUIState(
                    categoryList = updateList
                )
            }

            val categoryList = recipesRepository.getCategories()
            categoryList?.let {
                val updateList = it.map { category ->
                    category.copy(imageUrl = "${BASE_URL}images/${category.imageUrl}")
                }
                _categoriesUIState.value = CategoriesUIState(
                    categoryList = updateList
                )
                recipesRepository.insertCategoriesIntoCache(it)
            }
        }
    }

    data class CategoriesUIState(
        var categoryList: List<Category> = emptyList(),
    )
}

