package com.example.recipeapp.ui.categories


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.model.Category

class CategoriesListAdapter(
    private val fragment: Fragment,
) : RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    var dataSet: List<Category> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(categoryId: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

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

        with(viewHolder) {
            Glide
                .with(fragment)
                .load(currentItem.imageUrl)
                .centerCrop()
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_error)
                .into(ivCategoryImage)

            tvCategoryTitle.text = currentItem.title
            tvCategoryDescription.text = currentItem.description
            cvCategory.setOnClickListener {
                itemClickListener?.onItemClick(currentItem.id)
            }
        }
    }

    override fun getItemCount() = dataSet.size
}

