package com.example.recipeapp


import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.recipeapp.data.Recipe
import com.example.recipeapp.databinding.FragmentListRecipesBinding
import java.io.InputStream

class RecipesListFragment: Fragment() {

    private lateinit var binding: FragmentListRecipesBinding

    private var categoryId: Int? = null
    private var categoryName: String? = null
    private var categoryImageUrl: String? = null

    private val recipesList: List<Recipe> = STUB_RECIPES.burgerRecipes

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

       arguments?.let {
           categoryId = it.getInt(ARG_CATEGORY_ID)
           categoryName= it.getString(ARG_CATEGORY_NAME)
           categoryImageUrl = it.getString(ARG_CATEGORY_IMAGE_URL)
       }

        val headerImageView: ImageView = binding.ivHeaderRecipeCategory
        val categoryNameTextView: TextView = binding.tvHeaderRecipeCategory

        categoryNameTextView.text = categoryName
        try {
            val inputStream: InputStream? = context?.assets?.open(categoryImageUrl.toString())
            val drawable = Drawable.createFromStream(inputStream, null)
            headerImageView.setImageDrawable(drawable)
        } catch (ex: Exception) {
            Log.e("mylog", "Error: ${ex.stackTraceToString()}")
        }
        initRecycler()
    }

    private fun initRecycler() {
        val recyclerView = binding.rvRecipes
        val recipesListAdapter = RecipesListAdapter(recipesList, this)
        recipesListAdapter.setOnItemClickListener(object :
        RecipesListAdapter.OnItemClickListener{
            override fun onItemClick(categoryId: Int) {
                openRecipesByCategoryId(categoryId)
            }
        })
        recyclerView.adapter = recipesListAdapter
    }

    private fun openRecipesByCategoryId(categoryId: Int) {
        val recipe = recipesList.find { it.id == categoryId }

        val bundle = Bundle().apply {
            putParcelable(ARG_RECIPE, recipe)
        }

        parentFragmentManager.commit {
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
        }

    }

    companion object {
        const val ARG_RECIPE = "recipe"
    }

}