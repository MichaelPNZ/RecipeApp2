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
import com.example.recipeapp.databinding.FragmentListRecipesBinding
import java.io.InputStream

class RecipesListFragment: Fragment() {

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

       arguments?.let {
           categoryId = it.getInt(CategoriesListFragment.ARG_CATEGORY_ID)
           categoryName= it.getString(CategoriesListFragment.ARG_CATEGORY_NAME)
           categoryImageUrl = it.getString(CategoriesListFragment.ARG_CATEGORY_IMAGE_URL)
       }

        val headerImageView: ImageView = view.findViewById(R.id.ivHeaderRecipeCategory)
        val categoryNameTextView: TextView = view.findViewById(R.id.tvHeaderRecipeCategory)

        categoryNameTextView.text = categoryName
        try {
            val inputStream: InputStream? = context?.assets?.open(categoryImageUrl.toString())
            val drawable = Drawable.createFromStream(inputStream, null)
            headerImageView.setImageDrawable(drawable)
        } catch (ex: Exception) {
            Log.e("mylog", "Error: ${ex.stackTraceToString()}")
        }
    }

}