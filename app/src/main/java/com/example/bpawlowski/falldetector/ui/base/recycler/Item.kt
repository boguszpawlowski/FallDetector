package com.example.bpawlowski.falldetector.ui.base.recycler

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class Item<D, B : ViewDataBinding>(
    val data: D
) {

    protected lateinit var binding: B

    protected lateinit var itemView: View

    abstract val layoutResId: Int

    fun createHolder(itemView: View): ViewHolder {
        this.itemView = itemView
        binding = DataBindingUtil.bind(itemView) ?: throw RuntimeException("Couldn't bind this view")
        return ViewHolder(this, binding)
    }

    fun bind() {
        onBind()
        binding.executePendingBindings()
    }

    abstract fun onBind()

    open fun isSameAs(other: Item<*, *>): Boolean {
        return this === other
    }

    open fun hasSameContentAs(other: Item<*, *>): Boolean {
        return this.data == other.data
    }
}