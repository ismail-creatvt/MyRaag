package com.ismail.creatvt.myraag

import java.util.*

class MusicTimerTask(private val viewModel: MusicViewModel) : TimerTask() {
    override fun run() {
        if (viewModel.isPlaying.value == null ||
            viewModel.isPlaying.value == false ||
            viewModel.isSeeking) return

        val currentIndex = viewModel.currentIndex.value?:-1
        if (currentIndex == -1) return

        val currentSeconds = viewModel.currentSeconds.value ?: Duration.ZERO_SECONDS
        viewModel.currentSeconds.postValue(Duration.ofSeconds(currentSeconds.totalSeconds + 1))
    }
}