package com.example.bpawlowski.falldetector.util

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.bpawlowski.falldetector.R

fun loadContactImage(
    context: Context,
    uri: Uri,
    target: ImageView,
    cacheStrategy: DiskCacheStrategy = DiskCacheStrategy.ALL
) = Glide.with(context)
        .load(uri)
        .diskCacheStrategy(cacheStrategy)
        .transform(CircleCrop())
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(target)

fun ImageView.loadContactImage(
    context: Context,
    path: String?,
    cacheStrategy: DiskCacheStrategy = DiskCacheStrategy.ALL
) {
    if (path == null) {
        setImageResource(R.drawable.icon_contact)
    } else {
        val uri = Uri.parse(path)

        Glide.with(context)
            .load(uri)
            .diskCacheStrategy(cacheStrategy)
            .transform(CircleCrop())
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
    }
}
