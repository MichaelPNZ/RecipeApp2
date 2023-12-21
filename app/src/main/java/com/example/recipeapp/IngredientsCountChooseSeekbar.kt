package com.example.recipeapp

import android.widget.SeekBar

class IngredientsCountChooseSeekbar(
    seekBar: SeekBar,
    private val callback: (Int) -> Unit
) : SeekBar.OnSeekBarChangeListener {

    init {
        seekBar.setOnSeekBarChangeListener(this)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        callback.invoke(progress + 1)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }
}
