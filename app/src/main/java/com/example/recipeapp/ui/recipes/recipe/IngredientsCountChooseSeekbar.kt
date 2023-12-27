package com.example.recipeapp.ui.recipes.recipe

import android.widget.SeekBar

class IngredientsCountChooseSeekbar(
    private val onChangeIngredients: (Int) -> Unit,
) : SeekBar.OnSeekBarChangeListener {


    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        onChangeIngredients.invoke(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }
}
