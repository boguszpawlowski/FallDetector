package com.example.bpawlowski.falldetector.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat

fun Int.toBitmap(context: Context): Bitmap {
    val drawable = ContextCompat.getDrawable(context, this) ?: throw IllegalArgumentException("Could't find resource with id $this")
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)

    val canvas = Canvas(bitmap)
    drawable.draw(canvas)
    return bitmap
}