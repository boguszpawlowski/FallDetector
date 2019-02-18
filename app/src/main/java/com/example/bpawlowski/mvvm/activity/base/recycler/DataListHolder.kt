package com.example.bpawlowski.mvvm.activity.base.recycler

import android.databinding.BaseObservable

open class DataListHolder<out T : Any, out D : Any>(
    val group: T,
    val dataList: List<D>
): BaseObservable() {
    var isExpanded = true

    val itemCount = dataList.size

    open val groupDescription = "$group $itemCount items"
}