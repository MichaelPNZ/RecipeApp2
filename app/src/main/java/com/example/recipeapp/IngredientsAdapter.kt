package com.example.recipeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.data.Ingredient

class IngredientsAdapter(
    private val dataSet: List<Ingredient>,
) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    private var quantity = 1

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvIngredientDescription: TextView
        val tvIngredientQuantity: TextView

        init {
            tvIngredientDescription = view.findViewById(R.id.tvIngredientDescription)
            tvIngredientQuantity = view.findViewById(R.id.tvIngredientQuantity)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_ingredient, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentItem = dataSet[position]

        val calculatedQuantity = currentItem.quantity.toDouble() * quantity
        val formattedQuantity = if (calculatedQuantity % 1 != 0.0) {
            String.format("%.1f", calculatedQuantity)
        } else {
            calculatedQuantity.toInt().toString()
        }

        with(viewHolder) {
            tvIngredientDescription.text = currentItem.description
            tvIngredientQuantity.text = "$formattedQuantity ${currentItem.unitOfMeasure}"
        }

    }

    fun updateIngredients(progress: Int) {
        quantity = progress
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataSet.size
}