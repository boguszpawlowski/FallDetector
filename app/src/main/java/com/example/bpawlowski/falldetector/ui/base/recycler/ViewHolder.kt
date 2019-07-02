package com.example.bpawlowski.falldetector.ui.base.recycler

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(val item: Item<*, *>, binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    val swipeDirs = (item as? SwipeableItem)?.swipeDirs ?: 0

    val dragDirs = 0 //todo implement if necessary
}