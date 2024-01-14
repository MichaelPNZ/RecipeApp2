package com.example.recipeapp.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.recipeapp.data.RecipesRepository
import com.example.recipeapp.model.Category

class CategoriesViewModel : ViewModel() {

    private val recipesRepository = RecipesRepository()
    private val _categoriesUIState = MutableLiveData<CategoriesUIState>()
    val categoriesUIState: LiveData<CategoriesUIState>
        get() = _categoriesUIState

    fun loadCategories() {
        viewModelScope.launch {
            val categoryList = recipesRepository.getCategories()
            _categoriesUIState.value = categoryList?.let {
                CategoriesUIState(
                    categoryList = it
                )
            }
        }
    }

    data class CategoriesUIState(
        var categoryList: List<Category> = emptyList(),
    )

    fun getCategoryById(categoryId: Int): Category? {
        return _categoriesUIState.value?.categoryList?.find { it.id == categoryId }
    }
}

