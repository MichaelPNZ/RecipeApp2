package com.example.recipeapp.ui.recipes.recipe

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.recipeapp.R
import com.example.recipeapp.data.ARG_RECIPE_ID
import com.example.recipeapp.databinding.FragmentRecipeBinding
import java.io.InputStream

class RecipeFragment : Fragment() {

    private lateinit var binding: FragmentRecipeBinding
    private val viewModel: RecipeViewModel by viewModels()

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
            initRecycler(state)
        }
    }

    private fun initUI(state: RecipeViewModel.RecipeUIState) {
        binding.tvHeaderRecipe.text = state.recipe?.title.toString()

        try {
            val inputStream: InputStream? = context?.assets?.open(state.recipe?.imageUrl.toString())
            val bitmap = BitmapFactory.decodeStream(inputStream)
            binding.ivHeaderRecipe.setImageBitmap(bitmap)
        } catch (ex: Exception) {
            Log.e("mylog", "Error: ${ex.stackTraceToString()}")
        }

        val dividerHeight = resources.getDimensionPixelSize(R.dimen.divider_height)
        val dividerColor = context?.let { ContextCompat.getColor(it, R.color.dividerColor) }

        val itemDecoration = dividerColor?.let {
            SimpleDividerItemDecorationLastExcluded(dividerHeight, it)
        }
        itemDecoration?.let {
            binding.rvIngredients.addItemDecoration(it)
            binding.rvMethod.addItemDecoration(it)
        }

        addFavorites(state)
    }

    private fun initRecycler(state: RecipeViewModel.RecipeUIState) {
        val ingredientsAdapter = state.recipe?.let {
            IngredientsAdapter(it.ingredients)
        }
        val methodAdapter = state.recipe?.let {
            MethodAdapter(it.method)
        }

        val seekBar = binding.root.findViewById<SeekBar>(R.id.seekBar)

        IngredientsCountChooseSeekbar(seekBar) { count ->
            binding.tvSeekBarQuantity.text = (count).toString()
            ingredientsAdapter?.updateIngredients(count)
        }

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
}