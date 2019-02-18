package com.example.bpawlowski.mvvm.activity.main.recycler

import android.view.View
import com.example.bpawlowski.mvvm.R
import com.example.bpawlowski.mvvm.activity.base.recycler.AbstractRecyclerViewAdapter
import com.example.bpawlowski.mvvm.model.Post

class  PostViewAdapter(posts: List<Post>) : AbstractRecyclerViewAdapter<Post,PostItemHolder>(posts) {
    override fun getViewType(position: Int) = R.layout.item_post

    override fun createHolder(inflatedView: View, viewType: Int): PostItemHolder = PostItemHolder(inflatedView)
}