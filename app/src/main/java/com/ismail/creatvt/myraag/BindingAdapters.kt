package com.ismail.creatvt.myraag

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import android.widget.SeekBar
import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2

//Mark: ImageView binding adapters begin
@BindingAdapter(value = ["imageBitmap"])
fun setImageBitmap(imageView: ImageView, bitmap: Bitmap?) {
    if(bitmap == null) return
    imageView.setImageBitmap(bitmap)
}

@BindingAdapter(value = ["imageDrawable"])
fun setImageDrawable(imageView: ImageView, drawable: Drawable) {
    imageView.setImageDrawable(drawable)
}
//Mark: ImageView binding adapters end

//Mark: ViewPager2 binding adapters begin
@BindingAdapter(value = ["onPageSelected"])
fun setFragmentSelectedListener(viewPager2: ViewPager2, listener:OnPageSelectedListener){
    viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            listener.onPageSelected(position)
        }
    })
}

interface OnPageSelectedListener{
    fun onPageSelected(position:Int)
}

@BindingAdapter(value = ["currentItem"])
fun setCurrentItem(viewPager2: ViewPager2, currentItem: Int) {
    viewPager2.currentItem = currentItem
}
//Mark: ViewPager2 binding adapters end

//Mark: SeekBar binding adapters  begin
@BindingAdapter(value = ["onSeekStart", "onSeekProgress", "onSeekEnd"])
fun setSeekBarListener(
    seekBar: SeekBar?,
    onSeekStartListener: OnSeekStartListener?,
    onSeekProgressListener: OnSeekProgressListener?,
    onSeekEndListener: OnSeekEndListener?
) {
    seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            onSeekProgressListener?.onSeekProgress(seekBar, progress, fromUser)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
            onSeekStartListener?.onSeekStart(seekBar)
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            onSeekEndListener?.onSeekEnd(seekBar)
        }

    })
}

interface OnSeekStartListener {
    fun onSeekStart(seekBar: SeekBar?)
}

interface OnSeekProgressListener {
    fun onSeekProgress(seekBar: SeekBar?, progress: Int, fromUser: Boolean)
}

interface OnSeekEndListener {
    fun onSeekEnd(seekBar: SeekBar?)
}
//Mark: SeekBar binding adapters end