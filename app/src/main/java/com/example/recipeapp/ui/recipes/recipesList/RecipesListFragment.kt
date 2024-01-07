package com.example.recipeapp.ui.recipes.recipesList


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.recipeapp.databinding.FragmentListRecipesBinding

class RecipesListFragment : Fragment() {

    private val recipeListFragmentArgs: RecipesListFragmentArgs by navArgs()
    private val binding by lazy {
        FragmentListRecipesBinding.inflate(layoutInflater)
    }
    private val viewModel: RecipesListViewModel by viewModels()
    private val recipesListAdapter = RecipesListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        viewModel.recipesListUIState.observe(viewLifecycleOwner) { state ->
            with(binding) {
                tvHeaderRecipeCategory.text = state.categoryName
                ivHeaderRecipeCategory.setImageDrawable(state.categoryImage)

                recipesListAdapter.setOnItemClickListener(object : RecipesListAdapter.OnItemClickListener {
                    override fun onItemClick(categoryId: Int) {
                        openRecipesByCategoryId(categoryId)
                    }
                })

                recipesListAdapter.dataSet = state?.recipesList ?: emptyList()
                rvRecipes.adapter = recipesListAdapter
            }
        }
        viewModel.loadRecipesList(recipeListFragmentArgs.category)
    }

    private fun openRecipesByCategoryId(recipeId: Int) {
        findNavController().navigate(RecipesListFragmentDirections.actionRecipesListFragmentToRecipeFragment(recipeId))
    }
}