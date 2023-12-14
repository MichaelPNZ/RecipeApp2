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
import com.example.recipeapp.data.Recipe
import java.io.InputStream

class RecipesListAdapter(
private val dataSet: List<Recipe>,
private val fragment: CategoriesListFragment,
) : RecyclerView.Adapter<RecipesListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvRecipe: CardView
        val tvRecipeTitle: TextView
        val ivRecipeImage: ImageView

        init {
            cvRecipe = view.findViewById(R.id.cvRecipeItem)
            tvRecipeTitle = view.findViewById(R.id.tvRecipeName)
            ivRecipeImage = view.findViewById(R.id.ivRecipeImage)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_recipe, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentItem = dataSet[position]
        try {
            val inputStream: InputStream? = fragment.context?.assets?.open(currentItem.imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)
            viewHolder.ivRecipeImage.setImageDrawable(drawable)
        } catch (ex: Exception) {
            Log.e("mylog", "Error: ${ex.stackTraceToString()}")
        }

        with(viewHolder) {
            tvRecipeTitle.text = currentItem.title
            cvRecipe.setOnClickListener {
            }
        }

    }

    override fun getItemCount() = dataSet.size

}
