package com.example.bpawlowski.falldetector.activity.main.recycler

import com.example.bpawlowski.falldetector.activity.base.recycler.DataListHolder
import com.example.bpawlowski.falldetector.model.Post

class PostsListHolder(userId: Int, posts: List<Post>): DataListHolder<Int, Post>(userId, posts){
    override val groupDescription: String
        get() = "User $group, $itemCount posts"
}