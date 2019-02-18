package com.example.bpawlowski.falldetector.network

import com.example.bpawlowski.falldetector.model.Post
import io.reactivex.Observable
import retrofit2.http.GET

interface PostApi{

    @GET("/posts")
    fun getPosts(): Observable<List<Post>>

}