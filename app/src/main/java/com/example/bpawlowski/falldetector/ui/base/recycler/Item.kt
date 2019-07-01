package com.example.bpawlowski.falldetector.ui.base.recycler

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.bpawlowski.falldetector.util.doNothing

abstract class Item<D, B : ViewDataBinding>(
    val data: D,
    val onSwipeListener: ((D) -> Unit)? = null
) {

    protected lateinit var binding: B

    protected lateinit var itemView: View

    abstract val layoutResId: Int

    fun createHolder(itemView: View): RecyclerView.ViewHolder {
        this.itemView = itemView
        binding = DataBindingUtil.bind(itemView) ?: throw RuntimeException("Couldn't bind this view")
        return ViewHolder(binding)
    }

    fun bind() {
        onBind()
        binding.executePendingBindings()
    }

    abstract fun onBind()

    /**
     * Methods for swiping
     */

    open fun onSwipeStarted() = doNothing

    open fun onSwipeEnded() = doNothing

    open fun isSameAs(other: Item<*, *>): Boolean {
        return this == other
    }

    open fun hasSameContentAs(other: Item<*, *>): Boolean {
        return this.data == other.data
    }
}

class ViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)