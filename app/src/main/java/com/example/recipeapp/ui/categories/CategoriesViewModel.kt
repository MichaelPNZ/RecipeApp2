package com.example.recipeapp.ui.categories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.data.STUB
import com.example.recipeapp.model.Category

class CategoriesViewModel(application: Application) : AndroidViewModel(application) {

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

    fun getCategoryById(categoryId: Int): Category? {
        return STUB.getCategories().find { it.id == categoryId }
    }
}