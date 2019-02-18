package com.example.bpawlowski.falldetector.presentation.activity.base.recycler

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.bpawlowski.falldetector.presentation.util.layoutInflater

abstract class AbstractRecyclerViewAdapter<D, VH : AbstractViewHolder<*, D>>(data: List<D> = emptyList()) :
    RecyclerView.Adapter<VH>() {

    open var data: List<D> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        this.data = data
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = parent.context.layoutInflater.inflate(viewType, parent, false)
        return createHolder(view, viewType)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemViewType(position: Int): Int {
        return getViewType(position)
    }

    abstract fun getViewType(position: Int): Int
    abstract fun createHolder(inflatedView: View, viewType: Int): VH
}