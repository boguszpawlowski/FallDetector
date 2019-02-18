package com.example.bpawlowski.falldetector.activity.main

import android.arch.lifecycle.MutableLiveData
import com.example.bpawlowski.falldetector.activity.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.model.Post

class PostViewModel : BaseViewModel() {

    private val postTitle = MutableLiveData<String>()

    private val postBody = MutableLiveData<String>()
    fun bind(post: Post) {
        postBody.value = post.body
        postTitle.value = post.title
    }

    fun getPostTitle(): MutableLiveData<String> {
        return postTitle
    }

    fun getPostBody(): MutableLiveData<String> {
        return postBody
    }
}