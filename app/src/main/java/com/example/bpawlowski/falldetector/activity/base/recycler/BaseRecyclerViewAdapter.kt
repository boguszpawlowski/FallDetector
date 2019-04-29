package com.example.bpawlowski.falldetector.activity.base.recycler

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bpawlowski.falldetector.util.layoutInflater

abstract class BaseRecyclerViewAdapter<D, VH : BaseViewHolder<*, D>>(var items: MutableList<D> = mutableListOf()) :
    RecyclerView.Adapter<VH>() {

    fun updateData(value: MutableList<D>) {
        getDiffCallback(value)?.let {
            val diff = DiffUtil.calculateDiff(it)
            diff.dispatchUpdatesTo(this)
            items = value
            return
        }
        items = value
        notifyDataSetChanged()
    }

    open fun getDiffCallback(value: MutableList<D>): DiffUtil.Callback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = parent.context.layoutInflater.inflate(viewType, parent, false)
        return createHolder(view, viewType)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return getViewType(position)
    }

    abstract fun getViewType(position: Int): Int
    abstract fun createHolder(inflatedView: View, viewType: Int): VH
}