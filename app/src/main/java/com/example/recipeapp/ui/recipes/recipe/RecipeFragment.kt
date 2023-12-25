package com.example.recipeapp.ui.recipes.recipe

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
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
import com.example.recipeapp.data.ARG_RECIPE
import com.example.recipeapp.data.FAVORITES_KEY
import com.example.recipeapp.data.PREF_NAME
import com.example.recipeapp.R
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.databinding.FragmentRecipeBinding
import java.io.InputStream

class RecipeFragment : Fragment() {

    private lateinit var binding: FragmentRecipeBinding
    private var recipe: Recipe? = null
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
        viewModel.recipeUIState.observe(viewLifecycleOwner) { state ->
            Log.i("!!!", "isFavorite: ${state.isFavorite}")
        }

        initUI()
        initRecycler()
    }

    private fun initUI() {
        arguments?.let {
            recipe = if (Build.VERSION.SDK_INT >= 33) {
                it.getParcelable(ARG_RECIPE, Recipe::class.java)
            } else {
                it.getParcelable(ARG_RECIPE)
            }
        }

        binding.tvHeaderRecipe.text = recipe?.title
        binding.tvHeaderRecipe.text = recipe?.title

        try {
            val inputStream: InputStream? = context?.assets?.open(recipe?.imageUrl.toString())
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

        addFavorites()
    }

    private fun initRecycler() {
        val ingredientsAdapter = recipe?.let {
            IngredientsAdapter(it.ingredients)
        }
        val methodAdapter = recipe?.let {
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

    private fun saveFavorites(favorites: Set<String>) {
        val sharedPrefs = activity?.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        ) ?: return
        with(sharedPrefs.edit()) {
            putStringSet(FAVORITES_KEY, favorites)
            apply()
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

    private fun addFavorites() {
        val addFavoritesButton: ImageButton = binding.btnAddFavorite
        val checkIsFavorites = getFavorites()
        val buttonImage =
            if (checkIsFavorites.contains(recipe?.id.toString())) R.drawable.ic_heart
            else R.drawable.ic_heart_empty
        addFavoritesButton.setImageResource(buttonImage)

        addFavoritesButton.setOnClickListener {
            val allPreferences = getFavorites()
            if (allPreferences.contains(recipe?.id.toString())) {
                addFavoritesButton.setImageResource(R.drawable.ic_heart_empty)
                allPreferences.remove(recipe?.id.toString())
            } else {
                addFavoritesButton.setImageResource(R.drawable.ic_heart)
                allPreferences.add(recipe?.id.toString())
            }
            saveFavorites(allPreferences)
        }
        println(checkIsFavorites)
    }
}