package com.example.recipeapp.ui.recipes.recipesFavorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.databinding.FragmentFavoritesBinding
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
        favoritesListAdapter.setOnItemClickListener(object : RecipesListAdapter.OnItemClickListener {
            override fun onItemClick(categoryId: Int) {
                openRecipeByRecipeId(categoryId)
            }
        })
        binding.rvFavorites.adapter = favoritesListAdapter
        viewModel.favoritesUIState.observe(viewLifecycleOwner) { state ->

            with(binding) {
                if (state.recipe == null) tvFavoritesFragment.visibility = View.VISIBLE
                else tvFavoritesFragment.visibility = View.GONE
                favoritesListAdapter.submitList(state?.recipe ?: emptyList())
            }
        }
        viewModel.loadFavoritesRecipes()
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        findNavController().navigate(FavoritesFragmentDirections.actionFavoritesFragmentToRecipeFragment(recipeId))
    }
}