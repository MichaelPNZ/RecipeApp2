package com.example.recipeapp

import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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

        recipeNameTextView.text = recipe?.title
        try {
            val inputStream: InputStream? = context?.assets?.open(recipe?.imageUrl.toString())
            val bitmap = BitmapFactory.decodeStream(inputStream)
            headerImageView.setImageBitmap(bitmap)
        } catch (ex: Exception) {
            Log.e("mylog", "Error: ${ex.stackTraceToString()}")
        }

        binding.rvIngredients.setDivider(R.drawable.divider_drawable)
        binding.rvMethod.setDivider(R.drawable.divider_drawable)
    }

    private fun initRecycler() {
        val ingredientsAdapter = recipe?.let {
            IngredientsAdapter(it.ingredients)
        }
        val methodAdapter = recipe?.let {
            MethodAdapter(it.method)
        }

        binding.rvMethod.adapter = methodAdapter
        binding.rvIngredients.adapter = ingredientsAdapter
    }

}