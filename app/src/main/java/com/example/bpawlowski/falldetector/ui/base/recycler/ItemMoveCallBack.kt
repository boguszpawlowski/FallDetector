package com.example.bpawlowski.falldetector.ui.base.recycler

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.bpawlowski.falldetector.R

class ItemMoveCallBack(
    private val context: Context
) : TouchCallback() {

    private val background = ColorDrawable()
    private lateinit var itemBackgroundDrawable: Drawable
    private val backgroundColor = ContextCompat.getColor(context, R.color.accent)
    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.icon_delete)

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val itemView = viewHolder.itemView
            itemBackgroundDrawable = itemView.background

            itemView.background = ContextCompat.getDrawable(context, R.drawable.background_rounded_small)

            background.apply {
                color = backgroundColor
                setBounds(itemView.left, itemView.top, itemView.right, itemView.bottom)
            }.draw(c)

            deleteIcon?.calculateBounds(itemView, dX)?.draw(c)

            val alpha = ALPHA_FULL - Math.abs(dX) / itemView.width.toFloat()
            itemView.apply {
                this.alpha = alpha
                elevation = ELEVATION
                translationX = dX
            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE && viewHolder is ViewHolder) {
            viewHolder.item.onSwipeStarted()
        }

        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        val itemView = viewHolder.itemView

        itemView.alpha = ALPHA_FULL
        itemView.elevation = 0F
        itemView.background = itemBackgroundDrawable //todo not working

        deleteIcon?.setVisible(false, false)
        background.setVisible(false, false) //todo remove

        if (viewHolder is ViewHolder) {
            viewHolder.item.onSwipeEnded()
        }
    }

    override fun isItemViewSwipeEnabled() = true

    companion object {
        const val ALPHA_FULL = 1.0f
        const val ELEVATION = 8f
    }
}

fun Drawable.calculateBounds(itemView: View, dX: Float): Drawable {
    val itemHeight = itemView.bottom - itemView.top
    val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
    val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
    val deleteIconBottom = deleteIconTop + intrinsicHeight
    val deleteIconLeft: Int
    val deleteIconRight: Int
    if (dX > 0) {
        deleteIconLeft = itemView.left + deleteIconMargin - intrinsicWidth //fixme something is wrong here
        deleteIconRight = itemView.left + deleteIconMargin
    } else {
        deleteIconLeft = itemView.right - intrinsicWidth
        deleteIconRight = itemView.right
    }
    setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
    return this
}