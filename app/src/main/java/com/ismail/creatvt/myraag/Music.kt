package com.ismail.creatvt.myraag

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Parcelable
import android.provider.BaseColumns
import android.provider.MediaStore
import android.provider.MediaStore.Audio.AlbumColumns.ALBUM
import android.provider.MediaStore.Audio.ArtistColumns.ARTIST
import android.provider.MediaStore.Audio.PlaylistsColumns.DATE_ADDED
import android.provider.MediaStore.MediaColumns.*
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import kotlinx.android.parcel.Parcelize
import java.util.*


/**
 * Model class to store information about a music file in the memory
 */
@Parcelize
data class Music(
    val id: Int,
    val name: String,
    val dateAdded: Date,
    val album: String,
    val artist: String,
    val uri: Uri,
    val albumArt: Bitmap,
    val duration: Duration
) : Parcelable {
    companion object {

        //Music query params
        val orderBy = TITLE
        val baseUri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projections = arrayOf(
            _ID,
            TITLE,
            DATE_ADDED,
            ALBUM,
            ARTIST
        )

        /**
         * Creates a Music object from the current row pointed by the Cursor
         * @param cursor the cursor from which to take values
         * @return music object containing data from cursor
         */
        fun from(context: Context, cursor: Cursor): Music {

            cursor.apply {
                val id = getIntColumn(BaseColumns._ID)
                //Create URI for the music item
                val uri = Uri.withAppendedPath(baseUri, id.toString())

                return Music(
                    id = id,
                    name = getStrColumn(TITLE),
                    album = getStrColumn(ALBUM),
                    artist = getStrColumn(ARTIST),
                    dateAdded = DateUtils.fromSeconds(getIntColumn(DATE_ADDED)),
                    uri = uri,
                    albumArt = getImageBitmap(context, uri),
                    duration = getDuration(context, uri)
                )
            }

        }

        private fun getImageBitmap(context: Context, uri: Uri): Bitmap {
            val mediaMetadataRetriever = MediaMetadataRetriever()

            //set this music item as data source to retrieve information
            mediaMetadataRetriever.setDataSource(context, uri)

            var songImage: Bitmap
            try {
                val art = mediaMetadataRetriever.embeddedPicture
                val opt = BitmapFactory.Options()
                opt.inSampleSize = 2
                songImage = BitmapFactory.decodeByteArray(art, 0, art.size, opt)
            } catch (e: Exception) {
                songImage = BitmapFactory.decodeResource(context.resources, R.drawable.album_art)
            }
            return songImage
        }

        private fun getDuration(context: Context, uri: Uri): Duration {
            val mediaMetadataRetriever = MediaMetadataRetriever()

            //set this music item as data source to retrieve information
            mediaMetadataRetriever.setDataSource(context, uri)

            //extract duration metadata
            val metadata =
                mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)

            return Duration.ofSeconds(metadata.toInt() / 1000)
        }
    }


}