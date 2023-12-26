package com.example.recipeapp.ui.recipes.recipe

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R

class MethodAdapter : RecyclerView.Adapter<MethodAdapter.ViewHolder>() {

    var dataSet: List<String> = emptyList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMethodDescription: TextView

        init {
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
            tvMethodDescription.text = "${position + 1}. $currentItem"
        }
    }

    override fun getItemCount() = dataSet.size
}