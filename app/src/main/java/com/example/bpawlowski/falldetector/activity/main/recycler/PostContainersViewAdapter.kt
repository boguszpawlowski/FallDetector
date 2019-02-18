package com.example.bpawlowski.falldetector.activity.main.recycler

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.activity.base.recycler.AbstractRecyclerViewAdapter
import com.example.bpawlowski.falldetector.activity.base.recycler.AbstractViewHolder
import kotlinx.android.synthetic.main.posts_container.view.*

class PostContainersViewAdapter: AbstractRecyclerViewAdapter<PostsListHolder,AbstractViewHolder<*,PostsListHolder>>(){
    override fun getViewType(position: Int)= R.layout.posts_container

    override fun createHolder(inflatedView: View, viewType: Int): AbstractViewHolder<*, PostsListHolder> {
        return PostContainerViewHolder(inflatedView).apply {
            itemView.post_container_recycler.recycledViewPool = recyclerPool
            itemView.setOnClickListener {
                val data = data[adapterPosition]
                data.isExpanded = !data.isExpanded
            }
        }
    }

    private val recyclerPool = RecyclerView.RecycledViewPool()

}