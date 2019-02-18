package com.example.bpawlowski.mvvm.activity.main.recycler

import android.view.View
import com.android.databinding.library.baseAdapters.BR
import com.example.bpawlowski.mvvm.activity.base.recycler.AbstractViewHolder
import com.example.bpawlowski.mvvm.databinding.ItemPostBinding
import com.example.bpawlowski.mvvm.model.Post

class PostItemHolder(view: View): AbstractViewHolder<ItemPostBinding, Post>(view){
    override fun bindingId() = BR.Post

    override fun update(data: Post) {
        super.update(data)
        binding.postTitle.text = data.title
        binding.postBody.text = data.body
    }
}