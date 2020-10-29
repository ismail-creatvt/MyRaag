package com.ismail.creatvt.myraag

import android.view.MenuItem
import androidx.recyclerview.widget.DiffUtil

class MusicDiffCallback(
    val oldItems: ArrayList<Music>,
    val newItems: ArrayList<Music>
) :
    DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].id == newItems[newItemPosition].id
    }

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].toString() == newItems[newItemPosition].toString()
    }
}