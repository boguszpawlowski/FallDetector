package com.example.bpawlowski.falldetector.activity.main

import android.databinding.ObservableBoolean
import android.util.Log
import android.view.View
import com.example.bpawlowski.falldetector.activity.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.activity.main.recycler.PostsListHolder
import com.example.bpawlowski.falldetector.model.Post
import com.example.bpawlowski.falldetector.network.PostApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val postApi: PostApi
) : BaseViewModel() {

    private val TAG = MainViewModel::class.java.simpleName


    val disposable = CompositeDisposable()

    var isLoading: ObservableBoolean = ObservableBoolean(false)

    val errorClickListener = View.OnClickListener { loadPosts() }

    var postsSubject: BehaviorSubject<List<PostsListHolder>> = BehaviorSubject.create()

    init {
        loadPosts()
    }

    fun loadPosts() {
        disposable.add(
            postApi.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrievePostListStart() }
                .subscribe(
                    this::onRetrievePostListSuccess,
                    this::onRetrievePostListError,
                    this::onRetrievePostListFinish
                ))
    }

    private fun onRetrievePostListStart() {
        isLoading.set(true)
    }

    private fun onRetrievePostListFinish() {
        isLoading.set(false)
        postsSubject.onComplete()
    }

    private fun onRetrievePostListSuccess(data: List<Post>) {
        Log.e(TAG, " posts retrieved")
        val dataHolderList = data.groupBy { it.userId }
            .map { map ->
                PostsListHolder(map.key, map.value)
            }

        postsSubject.onNext(dataHolderList)
    }


    private fun onRetrievePostListError(throwable: Throwable) {
        isLoading.set(false)
        postsSubject.onError(throwable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }


}
