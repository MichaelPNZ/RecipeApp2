package com.example.recipeapp.ui.recipes.recipesList


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.model.Recipe

class RecipesListAdapter(
    private val fragment: Fragment? = null,
) : ListAdapter<Recipe, RecipesListAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }
    }
) {

    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(categoryId: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvRecipe: CardView = view.findViewById(R.id.cvRecipeItem)
        val tvRecipeTitle: TextView = view.findViewById(R.id.tvRecipeName)
        val ivRecipeImage: ImageView = view.findViewById(R.id.ivRecipeImage)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_recipe, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentItem = getItem(position)

        with(viewHolder) {
                if (fragment != null) {
                    Glide
                        .with(fragment)
                        .load(currentItem.imageUrl)
                        .centerCrop()
                        .placeholder(R.drawable.img_placeholder)
                        .error(R.drawable.img_error)
                        .into(ivRecipeImage)
                }

            tvRecipeTitle.text = currentItem.title
            cvRecipe.setOnClickListener {
                itemClickListener?.onItemClick(currentItem.id)
            }
        }
    }
}
