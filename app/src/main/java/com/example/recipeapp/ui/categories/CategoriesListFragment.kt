package com.example.recipeapp.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.databinding.FragmentListCategoriesBinding
import com.example.recipeapp.model.Category
import java.lang.IllegalArgumentException

class CategoriesListFragment : Fragment() {

    private val binding by lazy {
        FragmentListCategoriesBinding.inflate(layoutInflater)
    }
    private val viewModel: CategoriesViewModel by viewModels()
    private val categoriesListAdapter = CategoriesListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        viewModel.categoriesUIState.observe(viewLifecycleOwner) { state ->

            categoriesListAdapter.setOnItemClickListener(object :
                CategoriesListAdapter.OnItemClickListener {
                override fun onItemClick(categoryId: Int) {
                    openRecipesByCategoryId(categoryId)
                }
            })
            categoriesListAdapter.dataSet = state?.categoryList ?: emptyList()
            binding.rvCategories.adapter = categoriesListAdapter
        }
        viewModel.loadCategories()
    }

    private fun openRecipesByCategoryId(categoryId: Int) {
        val category: Category = viewModel.getCategoryById(categoryId) ?: throw IllegalArgumentException("Category not found for ID: $categoryId")
        findNavController().navigate(CategoriesListFragmentDirections
            .actionCategoriesListFragmentToRecipesListFragment(category))
    }
}