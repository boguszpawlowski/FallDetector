package com.example.bpawlowski.falldetector.util

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.bpawlowski.falldetector.R

fun loadContactImage(context: Context, uri: Uri, target: ImageView, radius: Int = 10, cacheStrategy: DiskCacheStrategy) {
	/*val progressDrawable = CircularProgressDrawable(context).apply {
		setColorSchemeColors(R.color.accent, R.color.accent, R.color.accent)
	}*/

	Glide.with(context)
		.load(uri)
		.transform(CenterCrop(), RoundedCorners(radius))
		.placeholder(R.drawable.icon_contact)
		.transition(DrawableTransitionOptions.withCrossFade())
		.into(target)
}
