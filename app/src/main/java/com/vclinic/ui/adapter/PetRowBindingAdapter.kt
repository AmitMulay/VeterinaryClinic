package com.vclinic.ui.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.vclinic.ui.ImageLoader

@BindingAdapter("displayImage")
fun displayImage(imageView: ImageView, url: String) {
    ImageLoader.showImage(url, imageView)
}