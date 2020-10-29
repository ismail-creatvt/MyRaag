package com.ismail.creatvt.myraag

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ismail.creatvt.myraag.databinding.ActivityPlayMusicBinding
import java.util.*

class PlayMusicActivity : AppCompatActivity(), PlayMusicPresenter.PlayMusicView {

    private var binding: ActivityPlayMusicBinding? = null
    private var presenter: IPlayMusicPresenter?=null
    private var musicTimer: TimerTask?=null

    companion object {
        private const val MUSIC_ID: String = "MUSIC_ITEM"

        fun getIntent(context: Context, musicId: Int): Intent {
            Intent(context, PlayMusicActivity::class.java).apply {
                putExtra(MUSIC_ID, musicId)
                return this
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.PlayerTheme)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_play_music)
        binding?.lifecycleOwner = this

        val musicId = intent.getIntExtra(MUSIC_ID, 0)

        val viewModel = ViewModelProvider(this).get(MusicViewModel::class.java)
        viewModel.setCurrentMusicId(musicId)

        presenter = PlayMusicPresenter(viewModel, this, this)

        musicTimer = MusicTimerTask(viewModel)

        binding?.viewModel = viewModel
        binding?.presenter = presenter

        binding?.songInfo?.adapter = AlbumAdapter(this, viewModel, this)

        Timer().scheduleAtFixedRate(musicTimer, 1000, 1000)
    }

    override fun onDestroy() {
        super.onDestroy()
        musicTimer?.cancel()
    }

    override fun createMediaPlayer(uri: Uri): MediaPlayer {
        return MediaPlayer.create(this, uri)
    }

}