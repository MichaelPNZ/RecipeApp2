package com.example.recipeapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.recipeapp.data.Recipe
import com.example.recipeapp.databinding.FragmentRecipeBinding
import java.io.InputStream

class RecipeFragment : Fragment() {

    private lateinit var binding: FragmentRecipeBinding

    private var recipe: Recipe? = null


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

        val headerImageView: ImageView = binding.ivHeaderRecipe
        val recipeNameTextView: TextView = binding.tvHeaderRecipe
        val addFavoritesButton: ImageButton = binding.btnAddFavorite
        var isLiked = false

        addFavoritesButton.setOnClickListener {
            isLiked = !isLiked
            val imageResource = if (isLiked) R.drawable.ic_heart else R.drawable.ic_heart_empty
            addFavoritesButton.setImageResource(imageResource)
        }

        recipeNameTextView.text = recipe?.title
        try {
            val inputStream: InputStream? = context?.assets?.open(recipe?.imageUrl.toString())
            val bitmap = BitmapFactory.decodeStream(inputStream)
            headerImageView.setImageBitmap(bitmap)
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
    }

    private fun initRecycler() {
        val ingredientsAdapter = recipe?.let {
            IngredientsAdapter(it.ingredients)
        }
        val methodAdapter = recipe?.let {
            MethodAdapter(it.method)
        }

        val seekBar = binding.root.findViewById<SeekBar>(R.id.seekBar)
        seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                binding.tvSeekBarQuantity.text = (progress + 1).toString()
                ingredientsAdapter?.updateIngredients(progress + 1)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        binding.rvMethod.adapter = methodAdapter
        binding.rvIngredients.adapter = ingredientsAdapter
    }

    private fun saveFavorites(set: Set<Recipe>) {
        val sharedPrefs = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
//        with (sharedPrefs.edit()) {
//            putInt(getString(R.string.saved_high_score_key), newHighScore)
//            apply()
//        }
    }

}