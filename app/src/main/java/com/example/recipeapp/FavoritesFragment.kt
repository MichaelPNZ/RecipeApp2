package com.example.recipeapp

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.recipeapp.databinding.FragmentFavoritesBinding
import java.io.InputStream

class FavoritesFragment: Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private var recipesList = STUB

    private var categoryId: Int? = null
    private var categoryName: String? = null
    private var categoryImageUrl: String? = null

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
        initUI()
        initRecycler()
    }

    private fun initUI() {
        arguments?.let {
            categoryId = it.getInt(ARG_CATEGORY_ID)
            categoryName = it.getString(ARG_CATEGORY_NAME)
            categoryImageUrl = it.getString(ARG_CATEGORY_IMAGE_URL)
        }

//        binding.tvHeaderRecipeCategory.text = categoryName

        try {
            val inputStream: InputStream? = context?.assets?.open(categoryImageUrl.toString())
            val bitmap = BitmapFactory.decodeStream(inputStream)
//            binding.ivHeaderRecipeCategory.setImageBitmap(bitmap)
        } catch (ex: Exception) {
            Log.e("mylog", "Error: ${ex.stackTraceToString()}")
        }
    }

    private fun initRecycler() {
        val allPreferences = RecipeFragment().getFavorites()
//        val recipesListAdapter = RecipesListAdapter()
//        recipesListAdapter.setOnItemClickListener()
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        val recipe = recipesList.getRecipeById(recipeId)

        val bundle = bundleOf(ARG_RECIPE to recipe)
        parentFragmentManager.commit {
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }

    }
}