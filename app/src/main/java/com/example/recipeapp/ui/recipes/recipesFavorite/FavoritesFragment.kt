package com.example.recipeapp.ui.recipes.recipesFavorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import com.example.recipeapp.R
import com.example.recipeapp.data.ARG_RECIPE_ID
import com.example.recipeapp.data.STUB
import com.example.recipeapp.databinding.FragmentFavoritesBinding
import com.example.recipeapp.ui.recipes.recipe.RecipeFragment
import com.example.recipeapp.ui.recipes.recipesList.RecipesListAdapter

class FavoritesFragment: Fragment() {

    private val binding by lazy {
        FragmentFavoritesBinding.inflate(layoutInflater)
    }
    private val viewModel: FavoritesViewModel by viewModels()
    private val favoritesListAdapter = RecipesListAdapter(this)

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
        viewModel.favoritesUIState.observe(viewLifecycleOwner) { state ->
            favoritesListAdapter.setOnItemClickListener(object : RecipesListAdapter.OnItemClickListener {
                override fun onItemClick(categoryId: Int) {
                    openRecipeByRecipeId(categoryId)
                }
            })

            with(binding) {
                if (state.recipe?.isEmpty() == true) tvFavoritesFragment.visibility = View.VISIBLE
                else tvFavoritesFragment.visibility = View.GONE

                favoritesListAdapter.dataSet = state?.recipe ?: emptyList()
                rvFavorites.adapter = favoritesListAdapter
            }
        }
        viewModel.loadFavoritesRecipes()
    }

    private fun openRecipeByRecipeId(categoryId: Int) {
        val recipeId = STUB.getRecipeById(categoryId).id
        val bundle = bundleOf(ARG_RECIPE_ID to recipeId)
        parentFragmentManager.commit {
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}