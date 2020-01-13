@file:Suppress("UNCHECKED_CAST")

package com.example.bpawlowski.falldetector.base.recycler
/*

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class Item<D, B : ViewDataBinding>(
    val data: D
) {
    protected lateinit var itemView: View

    abstract val layoutResId: Int

    fun createHolder(itemView: View): ViewHolder {
        val binding = DataBindingUtil.bind<B>(itemView) ?: throw RuntimeException("Couldn't bind this view")
        return ViewHolder(binding)
    }

    fun bind(holder: ViewHolder) {
        this.itemView = holder.itemView
        onBind(holder.binding as B)
    }

    abstract fun onBind(viewBinding: B)

    open fun isSameAs(other: Item<*, *>): Boolean {
        return this === other
    }

    open fun hasSameContentAs(other: Item<*, *>): Boolean {
        return this.data == other.data
    }
}*/
