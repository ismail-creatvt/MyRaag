package com.ismail.creatvt.myraag

import android.content.Context
import android.provider.MediaStore.Audio.Playlists.Members._ID
import android.provider.MediaStore.MediaColumns.DISPLAY_NAME
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MusicRepository {

    companion object {

        fun fetchMusicList(context: Context, onResult: (ArrayList<Music>) -> Unit) {
            CoroutineScope(IO).launch {
                val musicList = arrayListOf<Music>()

                context.contentResolver.query(
                    Music.baseUri,
                    Music.projections,
                    "$DISPLAY_NAME like ?",
                    arrayOf("%.mp3"),
                    Music.orderBy
                )?.apply {

                    while (moveToNext()) {
                        musicList.add(Music.from(context, this))
                    }

                    close()
                }

                withContext(Main){
                    onResult(musicList)
                }
            }

        }

        fun fetchMusicForId(context: Context, musicId: Int, onResult: (Music) -> Unit) {
            CoroutineScope(IO).launch {
                var music:Music?=null

                context.contentResolver.query(
                    Music.baseUri,
                    Music.projections,
                    "$_ID = $musicId",
                    null,
                    Music.orderBy
                )?.apply {

                    while (moveToNext()) {
                        music = Music.from(context, this)
                        break
                    }

                    close()
                }

                withContext(Main){
                    if(music != null) {
                        onResult(music!!)
                    }
                }
            }
        }

    }
}