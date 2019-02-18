package com.example.bpawlowski.mvvm.network

import com.example.bpawlowski.mvvm.model.Post
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET

interface PostApi{

    @GET("/posts")
    fun getPosts(): Observable<List<Post>>

}