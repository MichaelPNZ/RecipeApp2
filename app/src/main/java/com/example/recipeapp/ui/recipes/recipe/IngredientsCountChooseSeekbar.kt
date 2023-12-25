package com.example.recipeapp.ui.recipes.recipe

import android.widget.SeekBar

class IngredientsCountChooseSeekbar(
    seekBar: SeekBar,
    private val viewModel: RecipeViewModel,
    private val onChangeIngredients: (Int) -> Unit,
) : SeekBar.OnSeekBarChangeListener {

    init {
        seekBar.setOnSeekBarChangeListener(this)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        viewModel.onChangePortions(progress)
        onChangeIngredients.invoke(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }
}
