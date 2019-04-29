package com.example.bpawlowski.falldetector.util

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.renderscript.*
import android.view.View


fun getBitmapFromView(view: View?): Bitmap? {
    view?.let {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val c = Canvas(bitmap)
        view.layout(view.left, view.top, view.right, view.bottom)
        view.draw(c)
        return bitmap
    }
    return null
}

class RSBlurProcessor(private val rs: RenderScript) {

    fun blur(bitmap: Bitmap?, rad: Float, repeat: Int): Bitmap? {
        bitmap ?: return null

        if (!IS_BLUR_SUPPORTED) {
            return null
        }

        var radius = rad

        if (radius > MAX_RADIUS) {
            radius = MAX_RADIUS.toFloat()
        }

        val width = bitmap.width
        val height = bitmap.height

        // Create allocation type
        val bitmapType = Type.Builder(rs, Element.RGBA_8888(rs))
            .setX(width)
            .setY(height)
            .setMipmaps(false) // We are using MipmapControl.MIPMAP_NONE
            .create()

        // Create allocation
        val allocation = Allocation.createTyped(rs, bitmapType)

        // Create blur script
        val blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        blurScript!!.setRadius(radius)

        // Copy data to allocation
        allocation!!.copyFrom(bitmap)

        // set blur script input
        blurScript.setInput(allocation)

        // invoke the script to blur
        blurScript.forEach(allocation)

        // Repeat the blur for extra effect
        for (i in 0 until repeat) {
            blurScript.forEach(allocation)
        }

        // copy data back to the bitmap
        allocation.copyTo(bitmap)

        // release memory
        allocation.destroy()
        blurScript.destroy()
        return bitmap
    }


    @SuppressLint("ObsoleteSdkInt")
    private val IS_BLUR_SUPPORTED = Build.VERSION.SDK_INT >= 17
    private val MAX_RADIUS = 25
}