package com.ismail.creatvt.myraag

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.ismail.creatvt.myraag.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MusicListAdapter.MusicClickListener {

    companion object {
        private const val READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE"
        private const val READ_EXTERNAL_STORAGE_REQUEST: Int = 100
    }

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (isReadPermissionDenied()) {
            requestReadPermission()
            return
        }
        setUpMusicList()
    }

    private fun requestReadPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(READ_EXTERNAL_STORAGE),
            READ_EXTERNAL_STORAGE_REQUEST
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (isReadPermissionDenied()) {
            Toast.makeText(this, "Read permission denied", Toast.LENGTH_SHORT).show()
            return
        }

        setUpMusicList()
    }

    private fun isReadPermissionDenied(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_DENIED
    }

    private fun setUpMusicList() {
        val adapter = MusicListAdapter()

        MusicRepository.fetchMusicList(this){
            adapter.musicList = it
        }

        adapter.setMusicClickListener(this)

        binding?.adapter = adapter
    }

    override fun onMusicClick(music: Music) {
        startActivity(PlayMusicActivity.getIntent(this, music.id))
    }

}