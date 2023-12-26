package com.example.recipeapp.ui.recipes.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.recipeapp.R
import com.example.recipeapp.data.ARG_RECIPE_ID
import com.example.recipeapp.databinding.FragmentRecipeBinding

class RecipeFragment : Fragment() {

    private lateinit var binding: FragmentRecipeBinding
    private val viewModel: RecipeViewModel by viewModels()
    private val ingredientsAdapter = IngredientsAdapter()
    private val methodAdapter = MethodAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recipeId = arguments?.getInt(ARG_RECIPE_ID) ?: return
        viewModel.loadRecipe(recipeId)
        viewModel.recipeUIState.observe(viewLifecycleOwner) { state ->
            initUI(state)
        }
        addDecoration()
    }

    private fun initUI(state: RecipeViewModel.RecipeUIState) {
        binding.tvHeaderRecipe.text = state.recipe?.title.toString()
        binding.ivHeaderRecipe.setImageDrawable(state.recipeImage)

        addFavorites(state)

        val seekBar = binding.seekBar

        IngredientsCountChooseSeekbar(seekBar, viewModel) { progress ->
            viewModel.onChangePortions(progress)
        }
        ingredientsAdapter.dataSet = state.recipe?.ingredients ?: return
        methodAdapter.dataSet = state.recipe?.method ?: return

        binding.tvSeekBarQuantity.text = state.portionsCount.toString()
        binding.rvMethod.adapter = methodAdapter
        binding.rvIngredients.adapter = ingredientsAdapter
    }

    private fun addFavorites(state: RecipeViewModel.RecipeUIState) {
        val addFavoritesButton: ImageButton = binding.btnAddFavorite

        val buttonImage = if (state.isFavorite) R.drawable.ic_heart
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
            binding.rvIngredients.addItemDecoration(it)
            binding.rvMethod.addItemDecoration(it)
        }
    }
}