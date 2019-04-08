package com.example.bpawlowski.falldetector.activity.base.recycler

import androidx.databinding.BaseObservable

open class DataListHolder<out T : Any, out D : Any>(
    val group: T,
    val dataList: List<D>
): BaseObservable() {
    var isExpanded = true

    val itemCount = dataList.size

    open val groupDescription = "$group $itemCount items"
}