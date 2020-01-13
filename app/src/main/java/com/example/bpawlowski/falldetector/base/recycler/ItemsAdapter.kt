package com.example.bpawlowski.falldetector.base.recycler

/*

open class ItemsAdapter : RecyclerView.Adapter<ViewHolder>() {

    private var items = listOf<Item<*, *>>()
    private var lastItemForViewTypeLookup: Item<*, *>? = null

    fun update(value: List<Item<*, *>>) {
        val diff = DiffUtil.calculateDiff(getDiffCallback(value))
        items = value
        diff.dispatchUpdatesTo(this)
    }

    private fun getDiffCallback(value: List<Item<*, *>>): DiffUtil.Callback =
        DiffCallback(items.size, value.size, items, value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.context.layoutInflater.inflate(viewType, parent, false)
        val item = getItemForViewType(viewType)
        return item.createHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onViewRecycled(holder: ViewHolder) {
        holder.unbind()
    }

    override fun getItemViewType(position: Int): Int {
        lastItemForViewTypeLookup = items[position]
        return lastItemForViewTypeLookup?.layoutResId ?: throw RuntimeException("Invalid position $position")
    }

    inner class DiffCallback(
        private val oldBodyItemCount: Int,
        private val newBodyItemCount: Int,
        private val oldItems: List<Item<*, *>>,
        private val newItems: List<Item<*, *>>
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
            return oldItem.isSameAs(newItem)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]
            return oldItem.hasSameContentAs(newItem)
        }
    }

    private fun getItemForViewType(layoutResId: Int): Item<*, *> {
        lastItemForViewTypeLookup?.let {
            if (it.layoutResId == layoutResId) {
                return it
            }
        }

        for (i in 0 until itemCount) {
            val item = items[i]
            if (item.layoutResId == layoutResId) {
                return item
            }
        }
        throw IllegalStateException("Could not find model for view type: $layoutResId")
    }
}*/
