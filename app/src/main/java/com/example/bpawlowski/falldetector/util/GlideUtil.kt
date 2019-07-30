package com.example.bpawlowski.falldetector.util

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

fun loadContactImage(
	activity: Context,
	uri: Uri,
	target: ImageView,
	cacheStrategy: DiskCacheStrategy = DiskCacheStrategy.ALL
) {
	val circularProgressDrawable = CircularProgressDrawable(activity).apply {
		strokeWidth = 5f
		centerRadius = 30f
		start()
	}

	Glide.with(activity)
		.load(uri)
		.diskCacheStrategy(cacheStrategy)
		.transform(CircleCrop())
		.placeholder(circularProgressDrawable)
		.transition(DrawableTransitionOptions.withCrossFade())
		.into(target)
}
