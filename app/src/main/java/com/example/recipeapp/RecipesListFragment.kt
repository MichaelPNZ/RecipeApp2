package com.example.recipeapp

import android.annotation.SuppressLint
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

    @SuppressLint("DiscouragedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       arguments?.let {
           categoryId = it.getInt(ARG_CATEGORY_ID)
           categoryName= it.getString(ARG_CATEGORY_NAME)
           categoryImageUrl = it.getString(ARG_CATEGORY_IMAGE_URL)
       }

        val headerImageView: ImageView = view.findViewById(R.id.headerImageView)
        val categoryNameTextView: TextView = view.findViewById(R.id.tvCategoryNameTextView)

//        headerImageView.setImageDrawable(resources.assets.open(categoryImageUrl))
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