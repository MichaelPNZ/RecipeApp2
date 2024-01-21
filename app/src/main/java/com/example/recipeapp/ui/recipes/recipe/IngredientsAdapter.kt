package com.example.recipeapp.ui.recipes.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.model.Ingredient

class IngredientsAdapter : ListAdapter<Ingredient, IngredientsAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Ingredient>() {
        override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
            return oldItem.description == newItem.description
        }

        override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
            return oldItem == newItem
        }
    }
) {
    private var quantity = 1

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvIngredientDescription: TextView = view.findViewById(R.id.tvIngredientDescription)
        val tvIngredientQuantity: TextView = view.findViewById(R.id.tvIngredientQuantity)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_ingredient, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentItem = getItem(position)

        val doubleValue: Double? = try {
            currentItem.quantity.toDouble()
        } catch (e: NumberFormatException) {
            null
        }

        if (doubleValue != null) {
            val calculatedQuantity = doubleValue * quantity
            val formattedQuantity = if (calculatedQuantity % 1 != 0.0) {
                String.format("%.1f", calculatedQuantity)
            } else {
                calculatedQuantity.toInt().toString()
            }
            with(viewHolder) {
                tvIngredientDescription.text = currentItem.description
                tvIngredientQuantity.text = "$formattedQuantity ${currentItem.unitOfMeasure}"
            }
        } else {
            with(viewHolder) {
                tvIngredientDescription.text = currentItem.description
                tvIngredientQuantity.text = "${currentItem.quantity}"
            }
        }
    }

    fun updateIngredients(progress: Int) {
        quantity = progress
        notifyDataSetChanged()
    }
}