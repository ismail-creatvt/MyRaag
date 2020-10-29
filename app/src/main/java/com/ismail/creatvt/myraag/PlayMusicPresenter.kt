package com.ismail.creatvt.myraag

import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.widget.SeekBar
import androidx.lifecycle.LifecycleOwner
import com.ismail.creatvt.myraag.Duration.Companion.ZERO_SECONDS

class PlayMusicPresenter(
    private val viewModel: MusicViewModel,
    private val view: PlayMusicView,
    lifecycleOwner: LifecycleOwner
) : IPlayMusicPresenter {

    interface PlayMusicView {
        fun createMediaPlayer(uri: Uri): MediaPlayer
    }

    private val currentIndex: Int
        get() = viewModel.currentIndex.value ?: -1

    private val currentProgress: Int
        get() = viewModel.currentProgress.value ?: 0

    private val totalSeconds: Int
        get() = viewModel.currentMusic.value?.duration?.totalSeconds ?: 0

    private var musicPlayer: MediaPlayer? = null

    init {
        viewModel.currentIndex.observe(lifecycleOwner) {
            if (it >= 0) {
                viewModel.currentMusic.postValue(viewModel.musicList.value?.get(it))
            }
        }

        viewModel.currentSeconds.observe(lifecycleOwner) {
            val currentSeconds = viewModel.currentSeconds.value?.totalSeconds ?: 0
            val progress = ((currentSeconds.toFloat() / totalSeconds.toFloat()) * 100f).toInt()
            if (progress == 100) {
                onNext()
            } else{
                viewModel.currentProgress.postValue(progress)
            }
        }

        viewModel.currentMusic.observe(lifecycleOwner) {
            resetMusicPlayer(it)
        }
    }

    //Mark: IPlayMusicPresenter implementation begin

    /**
     * When user slides the album art and selects next or previous music item
     * then this method will be called with the position of the selected music item
     * @param position index of the music item in the list
     */
    override fun onMusicSelected(position: Int) {
        if (position == viewModel.currentIndex.value ?: -1) return
        viewModel.currentIndex.postValue(position)
        resetProgress()
    }

    /**
     * When user clicks on the play or pause button
     * performs the respective operation and changes the button
     */
    override fun onTogglePlay() {
        if (musicPlayer == null) return
        if (musicPlayer!!.isPlaying) {
            musicPlayer?.pause()
        } else {
            musicPlayer?.start()
        }
        viewModel.isPlaying.postValue(!(viewModel.isPlaying.value ?: false))
    }

    /**
     * When user clicks on the next button
     * checks if this is the last item in the list
     * if not then moves to the next item and starts playing it
     */
    override fun onNext() {
        resetProgress()
        nextMusic()
    }

    /**
     * When user clicks on the previous button
     * checks if the current music has played a substantial amount or not
     * if it has then it resets it to 0 and plays the current music item from start
     *
     * however, if the music item is already at the beginning then it simply moves
     * to the previous music item and starts it
     */
    override fun onPrevious() {
        resetProgress()
        if (currentProgress >= 2) {
            return
        }
        previousMusic()
    }

    override fun onSeekStart(seekBar: SeekBar?) {
        Log.d("PlayMusicPresenter", "onSeekStart")
        viewModel.isSeeking = true
    }

    /**
     * When user changes the progress of the current song through the seekbar
     */
    override fun onSeekProgress(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (fromUser) {
            Log.d("PlayMusicPresenter", "onSeekProgress : $progress")
            viewModel.currentSeconds.postValue(
                Duration.ofSeconds(
                    (totalSeconds.toFloat() * progress.toFloat() / 100f).toInt()
                )
            )
        }
    }

    override fun onSeekEnd(seekBar: SeekBar?) {
        Log.d("PlayMusicPresenter", "onSeekEnd")
        viewModel.isSeeking = false
        musicPlayer?.seekTo((viewModel.currentSeconds.value?.totalSeconds ?: 0) * 1000)
    }

    //Mark: IPlayMusicPresenter implementation end

    /**
     * decrements the currentIndex and starts playing the music
     */
    private fun previousMusic() {
        if (currentIndex <= 0) return
        viewModel.currentIndex.postValue(currentIndex - 1)
    }

    private fun resetProgress() {
        viewModel.currentSeconds.postValue(ZERO_SECONDS)
    }

    /**
     * increments the currentIndex and starts playing the music
     */
    private fun nextMusic() {
        val musicList = viewModel.musicList.value ?: return
        if (musicList.size - 1 == currentIndex) return
        viewModel.currentIndex.postValue(currentIndex + 1)
    }

    /**
     * takes the current music item according to the currentIndex
     * stops the player and starts the current music item
     */
    private fun resetMusicPlayer(music: Music) {
        if (musicPlayer?.isPlaying == true) {
            musicPlayer?.stop()
        }
        musicPlayer = view.createMediaPlayer(music.uri)
        musicPlayer?.start()
        viewModel.isPlaying.postValue(true)
    }

}