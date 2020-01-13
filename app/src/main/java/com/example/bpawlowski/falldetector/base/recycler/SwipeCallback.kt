package com.example.bpawlowski.falldetector.base.recycler

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyTouchHelper
import com.example.bpawlowski.falldetector.R

private const val ELEVATION_ON_SWIPE = 8f
private const val ELEVATION_FLAT = 0f

abstract class SwipeCallback<T : EpoxyModel<*>>(
    context: Context,
    val background: Drawable = ColorDrawable(ContextCompat.getColor(context, R.color.error)),
    val icon: Drawable? = ContextCompat.getDrawable(context, R.drawable.icon_delete)
) : EpoxyTouchHelper.SwipeCallbacks<T>() {

    override fun onSwipeProgressChanged(
        model: T,
        itemView: View,
        swipeProgress: Float,
        canvas: Canvas
    ) {
        if (swipeProgress == 0f) {
            canvas.apply {
                clear(background)
                clear(icon)
            }
        } else {
            with(background) {
                setBounds(itemView.left, itemView.top, itemView.right, itemView.bottom)
                draw(canvas)
            }
            setDrawableBounds(icon, itemView, swipeProgress)?.draw(canvas)

            itemView.translationZ = ELEVATION_ON_SWIPE
        }
    }

    override fun onSwipeStarted(model: T, itemView: View?, adapterPosition: Int) {
        super.onSwipeStarted(model, itemView, adapterPosition)

        itemView?.setBackgroundResource(R.color.swipeItem)
    }

    override fun clearView(model: T, itemView: View?) {
        itemView?.apply {
            translationZ = ELEVATION_FLAT
            setBackgroundResource(R.color.transparent)
        }
    }
}

private fun setDrawableBounds(drawable: Drawable?, itemView: View, swipeProgress: Float): Drawable? =
    drawable?.apply {
        val itemHeight = itemView.bottom - itemView.top

        val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val deleteIconBottom = deleteIconTop + intrinsicHeight

        val deleteIconMargin = intrinsicWidth

        val deleteIconLeft: Int
        val deleteIconRight: Int

        if (swipeProgress > 0F) {
            deleteIconLeft = itemView.left + deleteIconMargin
            deleteIconRight = itemView.left + deleteIconMargin + intrinsicWidth
        } else {
            deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth
            deleteIconRight = itemView.right - deleteIconMargin
        }
        setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
        return this
    }
