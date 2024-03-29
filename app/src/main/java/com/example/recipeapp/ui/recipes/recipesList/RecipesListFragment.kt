package com.example.recipeapp.ui.recipes.recipesList


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.recipeapp.R
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
        recipesListAdapter.setOnItemClickListener(object : RecipesListAdapter.OnItemClickListener {
            override fun onItemClick(categoryId: Int) {
                openRecipesByCategoryId(categoryId)
            }
        })
        binding.rvRecipes.adapter = recipesListAdapter
        viewModel.recipesListUIState.observe(viewLifecycleOwner) { state ->
            with(binding) {
                tvHeaderRecipeCategory.text = state.categoryName

                context?.let {
                    Glide
                        .with(it)
                        .load(state.categoryImageLink)
                        .centerCrop()
                        .placeholder(R.drawable.img_placeholder)
                        .error(R.drawable.img_error)
                        .into(ivHeaderRecipeCategory)
                }
                recipesListAdapter.submitList(state?.recipesList ?: emptyList())
            }
        }
        viewModel.loadRecipesList(recipeListFragmentArgs.categoryId)
    }

    private fun openRecipesByCategoryId(recipeId: Int) {
        findNavController().navigate(RecipesListFragmentDirections.actionRecipesListFragmentToRecipeFragment(recipeId))
    }
}