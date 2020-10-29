package com.ismail.creatvt.myraag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ismail.creatvt.myraag.databinding.FragmentAlbumArtBinding

class AlbumArtFragment : Fragment() {

    companion object {

        private const val MUSIC_ID = "MUSIC_ID"

        fun getInstance(music: Music) = AlbumArtFragment().apply {
            arguments = Bundle().apply {
                putInt(MUSIC_ID, music.id)
            }
            return this
        }
    }

    private var binding: FragmentAlbumArtBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_album_art,
            container,
            false
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        MusicRepository.fetchMusicForId(
            requireContext(),
            requireArguments().getInt(MUSIC_ID, 0)){music ->
            binding?.music = music
        }

    }

}