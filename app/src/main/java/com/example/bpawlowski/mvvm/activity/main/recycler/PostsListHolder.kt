package com.example.bpawlowski.mvvm.activity.main.recycler

import com.example.bpawlowski.mvvm.activity.base.recycler.DataListHolder
import com.example.bpawlowski.mvvm.model.Post

class PostsListHolder(userId: Int, posts: List<Post>): DataListHolder<Int, Post>(userId, posts){
    override val groupDescription: String
        get() = "User $group, $itemCount posts"
}