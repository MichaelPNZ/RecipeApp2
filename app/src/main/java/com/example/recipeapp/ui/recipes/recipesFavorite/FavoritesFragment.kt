package com.example.recipeapp.ui.recipes.recipesFavorite

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.recipeapp.data.ARG_RECIPE
import com.example.recipeapp.data.FAVORITES_KEY
import com.example.recipeapp.data.PREF_NAME
import com.example.recipeapp.R
import com.example.recipeapp.data.STUB
import com.example.recipeapp.databinding.FragmentFavoritesBinding
import com.example.recipeapp.ui.recipes.recipe.RecipeFragment
import com.example.recipeapp.ui.recipes.recipesList.RecipesListAdapter

class FavoritesFragment: Fragment() {

    private lateinit var binding: FragmentFavoritesBinding

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
        val allPreferences = getFavorites()
        val allKey = allPreferences.map { it.toInt() }.toSet()
        val filteredRecipes = STUB.getRecipesByIds(allKey)

        val favoritesListAdapter = RecipesListAdapter(filteredRecipes, this)
    favoritesListAdapter.setOnItemClickListener(object : RecipesListAdapter.OnItemClickListener {
        override fun onItemClick(categoryId: Int) {
            openRecipeByRecipeId(categoryId)
        }
    })
        if (filteredRecipes.isEmpty()) binding.tvFavoritesFragment.visibility = View.VISIBLE
        else binding.tvFavoritesFragment.visibility = View.GONE

        binding.rvFavorites.adapter = favoritesListAdapter
    }

    private fun openRecipeByRecipeId(categoryId: Int) {
        val recipe = STUB.getRecipeById(categoryId)
        val bundle = bundleOf(ARG_RECIPE to recipe)
        parentFragmentManager.commit {
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    private fun getFavorites(): MutableSet<String> {
        val sharedPrefs = activity?.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        val storedFavorites: Set<String>? = sharedPrefs?.getStringSet(FAVORITES_KEY, null)
        return HashSet(storedFavorites ?: emptySet())
    }
}