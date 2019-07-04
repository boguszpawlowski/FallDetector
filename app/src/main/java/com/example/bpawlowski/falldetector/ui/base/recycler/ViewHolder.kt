package com.example.bpawlowski.falldetector.ui.base.recycler

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    var item: Item<*, *>? = null
    val swipeDirs: Int
        get() = (item as? SwipeableItem)?.swipeDirs ?: 0
    val dragDirs = 0 //todo implement if necessary

    fun unbind() {
        item = null
    }

    fun bind(item: Item<*, *>) {
        this.item = item
    }
}