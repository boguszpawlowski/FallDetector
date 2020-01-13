package com.example.bpawlowski.falldetector.base.recycler

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.bpawlowski.falldetector.R

private const val DIRECTION_RIGHT = 1
private const val DIRECTION_LEFT = -1
private const val ELEVATION_SWIPE = 8f
private const val ELEVATION_FLAT = 0f

open class SwipeToDismissCallback(
    context: Context
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
    private val background = ColorDrawable(ContextCompat.getColor(context, R.color.secondary))

    private val deleteIconRight = ContextCompat.getDrawable(context, R.drawable.icon_delete)
    private val deleteIconLeft = ContextCompat.getDrawable(context, R.drawable.icon_delete)

    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
        // todo delete item
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: ViewHolder,
        target: ViewHolder
    ) = false

    override fun onChildDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val itemView = viewHolder.itemView
            if (dX == 0f) {
                canvas.clear(background)
                canvas.clear(deleteIconLeft)
                canvas.clear(deleteIconRight)
            } else {
                background.setBounds(itemView.left, itemView.top, itemView.right, itemView.bottom)
                background.draw(canvas)
                deleteIconRight?.calculateBounds(itemView, DIRECTION_RIGHT)?.draw(canvas)
                deleteIconLeft?.calculateBounds(itemView, DIRECTION_LEFT)?.draw(canvas)

                itemView.apply {
                    elevation = ELEVATION_SWIPE
                    translationX = dX
                }
            }
        }
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    override fun onSelectedChanged(viewHolder: ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            // todo notify View
            viewHolder?.itemView?.setBackgroundColor(viewHolder.itemView.context.getColor(R.color.swipeItem))
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        viewHolder.itemView.apply {
            setBackgroundColor(Color.TRANSPARENT)
            elevation = ELEVATION_FLAT
        }
    }

    override fun isItemViewSwipeEnabled() = true
}

fun Drawable.calculateBounds(itemView: View, direction: Int): Drawable {
    val itemHeight = itemView.bottom - itemView.top

    val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
    val deleteIconBottom = deleteIconTop + intrinsicHeight

    val deleteIconMargin = intrinsicWidth / 4

    val deleteIconLeft: Int
    val deleteIconRight: Int
    if (direction > 0) {
        deleteIconLeft = itemView.left + deleteIconMargin
        deleteIconRight = itemView.left + deleteIconMargin + intrinsicWidth
    } else {
        deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth
        deleteIconRight = itemView.right - deleteIconMargin
    }
    setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
    return this
}

fun Canvas.clear(drawable: Drawable?) {
    drawable?.setBounds(0, 0, 0, 0)
    drawable?.draw(this)
}
