package com.ismail.creatvt.myraag

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ismail.creatvt.myraag.Duration.Companion.ZERO_SECONDS
import java.util.ArrayList

class MusicViewModel(application: Application) : AndroidViewModel(application) {

    val currentMusic = MutableLiveData<Music>()

    private val _musicList = MutableLiveData<ArrayList<Music>>(arrayListOf())

    val musicList: LiveData<ArrayList<Music>>
        get() = _musicList

    val currentIndex = MutableLiveData(-1)

    val currentProgress = MutableLiveData(0)

    var currentSeconds = MutableLiveData(ZERO_SECONDS)

    val isPlaying = MutableLiveData(false)

    var isSeeking: Boolean = false

    init {
        MusicRepository.fetchMusicList(application) {
            _musicList.postValue(it)
            currentIndex.postValue(it.indexOfFirst { music ->
                currentMusic.value?.id == music.id
            })
        }
    }

    fun setCurrentMusicId(musicId: Int) {
        MusicRepository.fetchMusicForId(getApplication(), musicId){
                music ->
            currentMusic.postValue(music)
        }
    }
}