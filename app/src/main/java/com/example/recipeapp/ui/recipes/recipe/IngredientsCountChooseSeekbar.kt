package com.example.recipeapp.ui.recipes.recipe

import android.widget.SeekBar

class IngredientsCountChooseSeekbar(
    seekBar: SeekBar,
//    private val callback: (Int) -> Unit,
    private val viewModel: RecipeViewModel,
) : SeekBar.OnSeekBarChangeListener {

    init {
        seekBar.setOnSeekBarChangeListener(this)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        viewModel.onChangePortions(progress)
//        callback.invoke(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }
}
