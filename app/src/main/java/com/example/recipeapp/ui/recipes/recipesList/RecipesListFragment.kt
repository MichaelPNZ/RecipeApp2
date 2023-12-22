package com.example.recipeapp.ui.recipes.recipesList


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
import com.example.recipeapp.data.ARG_CATEGORY_ID
import com.example.recipeapp.data.ARG_CATEGORY_IMAGE_URL
import com.example.recipeapp.data.ARG_CATEGORY_NAME
import com.example.recipeapp.data.ARG_RECIPE
import com.example.recipeapp.R
import com.example.recipeapp.data.STUB
import com.example.recipeapp.databinding.FragmentListRecipesBinding
import com.example.recipeapp.ui.recipes.recipe.RecipeFragment
import java.io.InputStream

class RecipesListFragment : Fragment() {

    private lateinit var binding: FragmentListRecipesBinding

    private var categoryId: Int? = null
    private var categoryName: String? = null
    private var categoryImageUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListRecipesBinding.inflate(
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

        binding.tvHeaderRecipeCategory.text = categoryName

        try {
            val inputStream: InputStream? = context?.assets?.open(categoryImageUrl.toString())
            val bitmap = BitmapFactory.decodeStream(inputStream)
            binding.ivHeaderRecipeCategory.setImageBitmap(bitmap)
        } catch (ex: Exception) {
            Log.e("mylog", "Error: ${ex.stackTraceToString()}")
        }
    }

    private fun initRecycler() {
        val recipesListAdapter = categoryId?.let {
            STUB.getRecipesByCategoryId(it)
        }?.let { RecipesListAdapter(it, this) }
        recipesListAdapter?.setOnItemClickListener(object :
            RecipesListAdapter.OnItemClickListener {
            override fun onItemClick(categoryId: Int) {
                openRecipesByCategoryId(categoryId)
            }
        })
        binding.rvRecipes.adapter = recipesListAdapter
    }

    private fun openRecipesByCategoryId(categoryId: Int) {
        val recipe = STUB.getRecipeById(categoryId)

        val bundle = bundleOf(ARG_RECIPE to recipe)
        parentFragmentManager.commit {
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }

    }
}