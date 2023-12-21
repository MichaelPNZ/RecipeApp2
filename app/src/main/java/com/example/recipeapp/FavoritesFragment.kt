package com.example.recipeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.recipeapp.data.Recipe
import com.example.recipeapp.databinding.FragmentFavoritesBinding

class FavoritesFragment: Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private var recipe: Recipe? = null
    private var recipesList = STUB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(
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
        val allPreferences = RecipeFragment().getFavorites()
        println(allPreferences)
        val allKey = allPreferences.map { it.toInt() }.toSet()
        val filteredRecipes = recipesList.getRecipesByIds(allKey)

        val favoritesListAdapter = RecipesListAdapter(filteredRecipes, this)
    favoritesListAdapter.setOnItemClickListener(object : RecipesListAdapter.OnItemClickListener {
        override fun onItemClick(categoryId: Int) {
            openRecipeByRecipeId(categoryId)
        }
    })
        if (filteredRecipes.isEmpty()) {
            binding.tvFavoritesFragment.visibility = View.VISIBLE
        } else {
            binding.tvFavoritesFragment.visibility = View.GONE
        }
        binding.rvFavorites.adapter = favoritesListAdapter
    }

    private fun openRecipeByRecipeId(categoryId: Int) {
        val recipe = recipesList.getRecipeById(categoryId)

        val bundle = bundleOf(ARG_RECIPE to recipe)
        parentFragmentManager.commit {
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }

    }
}