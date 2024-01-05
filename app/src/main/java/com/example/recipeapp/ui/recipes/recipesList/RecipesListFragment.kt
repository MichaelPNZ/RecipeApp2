package com.example.recipeapp.ui.recipes.recipesList


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import com.example.recipeapp.data.ARG_CATEGORY_ID
import com.example.recipeapp.R
import com.example.recipeapp.data.ARG_RECIPE_ID
import com.example.recipeapp.databinding.FragmentListRecipesBinding
import com.example.recipeapp.ui.recipes.recipe.RecipeFragment

class RecipesListFragment : Fragment() {

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
        viewModel.loadRecipesList(arguments?.getInt(ARG_CATEGORY_ID) ?: return)
    }

    private fun openRecipesByCategoryId(categoryId: Int) {
        val bundle = bundleOf(ARG_RECIPE_ID to categoryId)
        parentFragmentManager.commit {
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}