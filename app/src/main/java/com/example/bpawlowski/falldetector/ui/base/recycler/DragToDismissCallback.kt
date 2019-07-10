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

class DragToDismissCallback(
	context: Context
) : TouchCallback() {

	private val background = ColorDrawable(ContextCompat.getColor(context, R.color.accent))
	private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.icon_delete)

	override fun onChildDraw(
		canvas: Canvas,
		recyclerView: RecyclerView,
		viewHolder: RecyclerView.ViewHolder,
		dX: Float,
		dY: Float,
		actionState: Int,
		isCurrentlyActive: Boolean
	) {
		if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
			val itemView = viewHolder.itemView
			if (dX == 0f) {
				background.clear(canvas)
				deleteIcon?.clear(canvas)
			} else {
				background.setBounds(itemView.left, itemView.top, itemView.right, itemView.bottom)
				background.draw(canvas)
				deleteIcon?.calculateBounds(itemView, dX)?.draw(canvas)

				itemView.apply {
					elevation = ELEVATION_SWIPE
					translationX = dX
				}
			}
		}
		super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
	}

	override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
		if (actionState != ItemTouchHelper.ACTION_STATE_IDLE && viewHolder is ViewHolder) {
			(viewHolder.item as? SwipeableItem)?.onSwipeStarted()
		}
		super.onSelectedChanged(viewHolder, actionState)
	}

	override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
		super.clearView(recyclerView, viewHolder)
		val itemView = viewHolder.itemView

		itemView.elevation = ELEVATION_FLAT

		if (viewHolder is ViewHolder) {
			(viewHolder.item as? SwipeableItem)?.onSwipeEnded()
		}
	}

	override fun isItemViewSwipeEnabled() = true

	companion object {
		const val ELEVATION_SWIPE = 8f
		const val ELEVATION_FLAT = 0f
	}
}

fun Drawable.calculateBounds(itemView: View, dX: Float): Drawable { //fixme fixed bounds
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

fun Drawable.clear(c: Canvas) {
	setBounds(0, 0, 0, 0)
	draw(c)
}