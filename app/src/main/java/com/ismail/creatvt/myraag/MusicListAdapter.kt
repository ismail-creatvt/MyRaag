package com.ismail.creatvt.myraag

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ismail.creatvt.myraag.databinding.MusicListItemBinding

class MusicListAdapter : RecyclerView.Adapter<MusicListAdapter.MusicViewHolder>() {

    interface MusicClickListener{
        fun onMusicClick(music:Music)
    }

    private var musicClickListener:MusicClickListener?=null

    fun setMusicClickListener(listener:MusicClickListener){
        musicClickListener = listener
    }

    var musicList = arrayListOf<Music>()
        set(value) {
            val olditems = field
            field = value
            DiffUtil.calculateDiff(MusicDiffCallback(olditems, value)).dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MusicViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.music_list_item,
            parent,
            false
        )
    )

    override fun getItemCount() = musicList.size

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        holder.itemBinding.music = musicList[position]
        holder.itemBinding.listener = musicClickListener
    }

    class MusicViewHolder(val itemBinding: MusicListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

}