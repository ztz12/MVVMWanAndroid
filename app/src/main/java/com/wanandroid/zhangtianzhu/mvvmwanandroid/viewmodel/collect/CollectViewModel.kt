package com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.collect

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.wanandroid.CollectSourceChange
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.CollectionArticle
import com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.collect.CollectDataRepository
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.Injection

class CollectViewModel constructor(application: Application) : AndroidViewModel(application) {
    private var mCurrentPage = 0

    val collectViewModel = CollectDataRepository.getInstance().getDataRepository()

    private val _collectionArticle = MutableLiveData<MutableList<CollectionArticle>>()

    val collectionArticle: LiveData<MutableList<CollectionArticle>>
        get() = _collectionArticle

    private val _removeSuccess = MutableLiveData<Boolean>()
    val removeSuccess: LiveData<Boolean>
        get() = _removeSuccess

    fun retry() {
        CollectDataRepository.getInstance().getListingRepository()
    }

    fun refresh() {
        CollectDataRepository.getInstance().getListingRepository()
    }

    fun getCollectList(page: Int) {
        Injection.provideCollectRemote().getCollectList(page, object : CollectSourceChange.CollectCallback {
            override fun success(data: MutableList<CollectionArticle>) {
                _collectionArticle.value = data
            }

            override fun failure(t: Throwable) {
            }

        })
    }

    fun removeCollectItem(id: Int, originId: Int) {
        Injection.provideCollectRemote().getWanAndroidData(id, originId, object : CollectSourceChange.BaseCallback {
            override fun success() {
                _removeSuccess.value = true
            }

            override fun failure(t: Throwable) {
                _removeSuccess.value = false
            }

        })
    }

    fun refreshData() {
        mCurrentPage = 0
        getCollectList(mCurrentPage)
    }

    fun loadMore() {
        mCurrentPage++
        getCollectList(mCurrentPage)
    }
}