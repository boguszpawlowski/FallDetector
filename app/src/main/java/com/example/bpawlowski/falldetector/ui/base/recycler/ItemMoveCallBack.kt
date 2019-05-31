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
    context: Context,
    private val adapter: ItemTouchHelperAdapter
) : ItemTouchHelper.Callback() {

    private val background = ColorDrawable()
    private val backgroundColor = ContextCompat.getColor(context, R.color.colorAccent)
    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.icon_delete)

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(0,swipeFlags)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter.onItemDismiss(viewHolder.adapterPosition)
    }

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
            val direction = if (dX > 0) itemView.left else itemView.right

            background.apply {
                color = backgroundColor
                setBounds(direction + dX.toInt(), itemView.top, direction, itemView.bottom)
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
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE && viewHolder is ItemTouchHelperViewHolder) {
            val itemViewHolder = viewHolder as ItemTouchHelperViewHolder
            itemViewHolder.onItemSelected()
        }

        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)

        viewHolder.itemView.alpha = ALPHA_FULL

        if (viewHolder is ItemTouchHelperViewHolder) {
            val itemViewHolder = viewHolder as ItemTouchHelperViewHolder
            itemViewHolder.onItemClear()
        }
    }
    override fun isItemViewSwipeEnabled() = true

    companion object {
        const val ALPHA_FULL = 1.0f
        const val ELEVATION = 10f
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
        deleteIconLeft = itemView.left + deleteIconMargin - intrinsicWidth
        deleteIconRight = itemView.left + deleteIconMargin
    } else {
        deleteIconLeft = itemView.right - deleteIconMargin + intrinsicWidth
        deleteIconRight = itemView.right - deleteIconMargin
    }
    setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
    return this
}