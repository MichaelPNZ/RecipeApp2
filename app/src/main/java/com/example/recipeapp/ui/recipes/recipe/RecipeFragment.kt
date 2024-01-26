package com.example.recipeapp.ui.recipes.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipeBinding

class RecipeFragment : Fragment() {

    private val binding by lazy {
        FragmentRecipeBinding.inflate(layoutInflater)
    }
    private val recipeFragmentArgs: RecipeFragmentArgs by navArgs()
    private val viewModel: RecipeViewModel by viewModels()
    private val ingredientsAdapter = IngredientsAdapter()
    private val methodAdapter = MethodAdapter()

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
        addDecoration()
    }

    private fun initUI() {
        val seekBar = binding.seekBar
        seekBar.setOnSeekBarChangeListener(IngredientsCountChooseSeekbar { progress ->
            viewModel.onChangePortions(progress)
            ingredientsAdapter.updateIngredients(progress)
        })

        binding.rvMethod.adapter = methodAdapter
        binding.rvIngredients.adapter = ingredientsAdapter
        viewModel.recipeUIState.observe(viewLifecycleOwner) { state ->
            with(binding) {
                tvHeaderRecipe.text = state.recipe?.title.toString()

                context?.let {
                    Glide
                        .with(it)
                        .load(state.recipeImage)
                        .centerCrop()
                        .placeholder(R.drawable.img_placeholder)
                        .error(R.drawable.img_error)
                        .into(ivHeaderRecipe)
                }

                addFavorites(state)
                ingredientsAdapter.submitList(state.recipe?.ingredients ?: emptyList())
                methodAdapter.submitList(state.recipe?.method ?: emptyList())
                tvSeekBarQuantity.text = state.portionsCount.toString()
            }
        }
        viewModel.loadRecipe(recipeFragmentArgs.recipeId)
    }

    private fun addFavorites(state: RecipeViewModel.RecipeUIState) {
        val addFavoritesButton: ImageButton = binding.btnAddFavorite

        val buttonImage = if (state.recipe?.isFavorite == true) R.drawable.ic_heart
        else R.drawable.ic_heart_empty

        addFavoritesButton.setImageResource(buttonImage)
        addFavoritesButton.setOnClickListener {
            viewModel.onFavoritesClicked()
        }
    }

    private fun addDecoration() {
        val dividerHeight = resources.getDimensionPixelSize(R.dimen.divider_height)
        val dividerColor = context?.let { ContextCompat.getColor(it, R.color.dividerColor) }

        val itemDecoration = dividerColor?.let {
            SimpleDividerItemDecorationLastExcluded(dividerHeight, it)
        }
        itemDecoration?.let {
            with(binding) {
                rvIngredients.addItemDecoration(it)
                rvMethod.addItemDecoration(it)
            }
        }
    }
}