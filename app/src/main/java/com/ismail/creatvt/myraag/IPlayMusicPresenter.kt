package com.ismail.creatvt.myraag

import android.widget.SeekBar

interface IPlayMusicPresenter {

    fun onTogglePlay()

    fun onNext()

    fun onPrevious()

    fun onMusicSelected(position:Int)

    fun onSeekStart(seekBar: SeekBar?)

    fun onSeekProgress(seekBar: SeekBar?, progress: Int, fromUser: Boolean)

    fun onSeekEnd(seekBar: SeekBar?)
}