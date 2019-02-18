package com.example.bpawlowski.falldetector.activity.base.recycler

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View
import java.lang.NullPointerException

abstract class AbstractViewHolder<out B: ViewDataBinding, in D>(view: View): RecyclerView.ViewHolder(view){

    val binding: B = DataBindingUtil.bind(view) ?: throw NullPointerException("no binding")

    abstract fun bindingId(): Int

    fun bind(data: D){
        update(data)
        binding.setVariable(bindingId(), data)
        binding.executePendingBindings()
    }

    open fun update(data: D){

    }
}