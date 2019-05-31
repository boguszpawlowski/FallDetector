package com.example.bpawlowski.falldetector.ui.base.recycler

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bpawlowski.falldetector.util.layoutInflater

abstract class BaseRecyclerViewAdapter<D, VH : BaseViewHolder<*, D>>(var items: MutableList<D> = mutableListOf()) :
    RecyclerView.Adapter<VH>() {

    fun updateData(value: MutableList<D>) {
        val diff = DiffUtil.calculateDiff(getDiffCallback(value))
        diff.dispatchUpdatesTo(this)
        items = value
    }

    private fun getDiffCallback(value: MutableList<D>): DiffUtil.Callback =
        DiffCallback(items.size, value.size, items, value)

    open fun areItemsSame(oldItem: D, newItem: D) = oldItem == newItem

    open fun areContentsSame(oldItem: D, newItem: D) = oldItem === newItem

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = parent.context.layoutInflater.inflate(viewType, parent, false)
        return createHolder(view, viewType)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int) = getViewType(position)  //FixMe create Items

    abstract fun getViewType(position: Int): Int

    abstract fun createHolder(inflatedView: View, viewType: Int): VH

    inner class DiffCallback(
        private val oldBodyItemCount: Int,
        private val newBodyItemCount: Int,
        private val oldItems: List<D>,
        private val newItems: List<D>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldBodyItemCount
        }

        override fun getNewListSize(): Int {
            return newBodyItemCount
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]
            return areItemsSame(oldItem, newItem)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]
            return areContentsSame(oldItem, newItem)
        }
    }
}