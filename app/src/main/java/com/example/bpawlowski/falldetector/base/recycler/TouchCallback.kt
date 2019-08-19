package com.example.bpawlowski.falldetector.base.recycler

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

abstract class TouchCallback : ItemTouchHelper.SimpleCallback(0, 0) {

    override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return (viewHolder as ViewHolder).swipeDirs
    }

    override fun getDragDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return (viewHolder as ViewHolder).dragDirs
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val swipeFlags = (viewHolder as ViewHolder).swipeDirs
        val dragFlags = viewHolder.dragDirs
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        ((viewHolder as? ViewHolder)?.item as? SwipeableItem)?.onDismissed()
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false
}