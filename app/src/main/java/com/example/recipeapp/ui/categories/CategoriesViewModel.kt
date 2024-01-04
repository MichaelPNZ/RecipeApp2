package com.example.recipeapp.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.data.STUB
import com.example.recipeapp.model.Category

class CategoriesViewModel : ViewModel() {

    private val _categoriesUIState = MutableLiveData<CategoriesUIState>()
    val categoriesUIState: LiveData<CategoriesUIState>
        get() = _categoriesUIState

    data class CategoriesUIState(
        var categoryList: List<Category>? = null,
    )

    fun loadCategories() {
        val categoryList = STUB.getCategories()
        _categoriesUIState.value = CategoriesUIState(
            categoryList = categoryList
        )
    }
}