package com.example.bpawlowski.falldetector.ui.base.recycler

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    var item: Item<*, *>? = null

    val swipeDirs: Int
        get() = (item as? SwipeableItem)?.swipeDirs ?: 0

    val dragDirs = 0

    fun unbind() {
        item = null
    }

    fun <B : ViewDataBinding> bind(item: Item<*, B>) {
        this.item = item
        item.bind(this)
        binding.executePendingBindings()
    }
}