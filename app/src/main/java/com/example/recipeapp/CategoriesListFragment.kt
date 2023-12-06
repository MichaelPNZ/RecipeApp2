package com.example.recipeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.data.Category
import com.example.recipeapp.databinding.FragmentListCategoriesBinding

class CategoriesListFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentListCategoriesBinding
    private var categoryList: List<Category> = mutableListOf(
        Category(0, "Бургеры", "Рецепты всех популярных видов бургеров", "burger.png"),
        Category(1, "Десерты", "Самые вкусные рецепты десертов специально для вас", "dessert.png"),
        Category(2, "Пицца", "Пицца на любой вкус и цвет. Лучшая подборка для тебя", "pizza.png"),
        Category(3, "Рыба", "Печеная, жареная, сушеная, любая рыба на твой вкус", "fish.png"),
        Category(4, "Супы", "От классики до экзотики: мир в одной тарелке", "soup.png"),
        Category(5, "Салаты", "Хрустящий калейдоскоп под соусом вдохновения", "salad.png"),
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListCategoriesBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        recyclerView = binding.rvCategories
        val categoriesListAdapter = CategoriesListAdapter(categoryList, this)
        recyclerView.adapter = categoriesListAdapter
    }
}