package com.example.recipeapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.data.Recipe

class MethodAdapter(
    private val dataSet: List<String>,
    private val fragment: RecipeFragment,
) : RecyclerView.Adapter<MethodAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val cvMethod: CardView
        val tvMethodDescription: TextView

        init {
            cvMethod = view.findViewById(R.id.cvMethodItem)
            tvMethodDescription = view.findViewById(R.id.tvMethodDescription)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_method, viewGroup, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentItem = dataSet[position]

        with(viewHolder) {
            tvMethodDescription.text = currentItem
        }
    }

    override fun getItemCount() = dataSet.size
}