package com.example.recipeapp

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.data.Category
import java.io.InputStream

class CategoriesListAdapter(
    private val dataSet: List<Category>,
    private val fragment: CategoriesListFragment
) : RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvCategory: CardView
        val tvCategoryTitle: TextView
        val tvCategoryDescription: TextView
        val ivCategoryImage: ImageView

        init {
            cvCategory = view.findViewById(R.id.cvCategoryItem)
            tvCategoryTitle = view.findViewById(R.id.tvCategoryName)
            tvCategoryDescription = view.findViewById(R.id.tvCategoryDescription)
            ivCategoryImage = view.findViewById(R.id.ivCategoryImage)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_category, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentItem = dataSet[position]
        try {
            val inputStream: InputStream? = fragment.context?.assets?.open(currentItem.imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)
            viewHolder.ivCategoryImage.setImageDrawable(drawable)
        } catch (ex: Exception) {
            Log.e("mylog", "Error: ${ex.stackTraceToString()}")
        }

        with(viewHolder) {
            tvCategoryTitle.text = currentItem.title
            tvCategoryDescription.text = currentItem.description
            cvCategory.setOnClickListener {

            }
        }

    }

    override fun getItemCount() = dataSet.size

}