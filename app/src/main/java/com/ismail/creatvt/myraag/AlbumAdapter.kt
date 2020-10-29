package com.ismail.creatvt.myraag

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter

class AlbumAdapter(
    fragmentActivity: FragmentActivity,
    viewModel: MusicViewModel,
    lifecyclerOwner: LifecycleOwner
) : FragmentStateAdapter(fragmentActivity) {

    private var musicList:ArrayList<Music> = arrayListOf()
            set(value) {
                val oldItems = field
                field = value
                DiffUtil.calculateDiff(MusicDiffCallback(oldItems, field)).dispatchUpdatesTo(this)
            }

    init {
        viewModel.musicList.observe(lifecyclerOwner){
            musicList = it
        }
    }

    override fun getItemCount() = musicList.size

    override fun createFragment(position: Int): Fragment {
        return AlbumArtFragment.getInstance(musicList[position])
    }

}