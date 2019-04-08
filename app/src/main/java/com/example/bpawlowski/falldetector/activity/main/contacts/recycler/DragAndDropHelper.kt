package com.example.bpawlowski.falldetector.activity.main.contacts.recycler

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class DragAndDropHelper(): ItemTouchHelper.Callback(){
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isLongPressDragEnabled() = true

    //TODO finish this class implementation
}