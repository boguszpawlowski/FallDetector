package com.example.bpawlowski.falldetector.activity.main.recycler

import android.support.v7.widget.DividerItemDecoration
import android.view.View
import com.android.databinding.library.baseAdapters.BR
import com.example.bpawlowski.falldetector.activity.base.recycler.AbstractViewHolder
import com.example.bpawlowski.falldetector.databinding.PostsContainerBinding

class PostContainerViewHolder(
    view: View
) : AbstractViewHolder<PostsContainerBinding, PostsListHolder>(view) {
    override fun bindingId() = BR.postContainer

    override fun update(data: PostsListHolder) {
        super.update(data)
        binding.postContainerRecycler.apply {
            adapter = PostViewAdapter(data.dataList)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }
}