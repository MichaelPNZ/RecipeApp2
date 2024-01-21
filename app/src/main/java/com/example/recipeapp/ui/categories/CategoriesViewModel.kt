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

            if (categoryListCache.isEmpty()) {
                val categoryList = recipesRepository.getCategories()

                categoryList?.forEach {
                    it.imageUrl = "${BASE_URL}images/${it.imageUrl}"
                }

                _categoriesUIState.value = categoryList?.let {
                    CategoriesUIState(
                        categoryList = it
                    )
                }
                _categoriesUIState.value?.categoryList?.forEach { recipesRepository.insertDao(it)}
            } else {
                categoryListCache.forEach {
                    it.imageUrl = "${BASE_URL}images/${it.imageUrl}"
                }
                _categoriesUIState.value = CategoriesUIState(
                    categoryList = categoryListCache
                )
            }
        }
    }

    data class CategoriesUIState(
        var categoryList: List<Category> = emptyList(),
    )
}

