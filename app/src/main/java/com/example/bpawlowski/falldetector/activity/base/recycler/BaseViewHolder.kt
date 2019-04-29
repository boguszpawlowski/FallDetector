package com.example.bpawlowski.falldetector.activity.base.recycler

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import java.lang.NullPointerException

abstract class BaseViewHolder<out B: ViewDataBinding, in D>(view: View): RecyclerView.ViewHolder(view){

    val binding: B = DataBindingUtil.bind(view) ?: throw NullPointerException("no binding")

    abstract fun bindingId(): Int

    fun bind(data: D){
        onBind(data)
        binding.setVariable(bindingId(), data)
        binding.executePendingBindings()
    }

    /**
     * for additional action when binding
     */
    abstract fun onBind(data: D)
}