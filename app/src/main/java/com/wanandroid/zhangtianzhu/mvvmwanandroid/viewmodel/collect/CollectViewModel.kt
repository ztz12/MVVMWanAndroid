package com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.collect

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.wanandroid.CollectSourceChange
import com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.collect.CollectDataRepository
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.Injection

class CollectViewModel constructor(application: Application) : AndroidViewModel(application) {
    val collectViewModel = CollectDataRepository.getInstance().getDataRepository()
    private val _removeSuccess = MutableLiveData<Boolean>()
    val removeSuccess: LiveData<Boolean>
        get() = _removeSuccess

    fun retry() {
        CollectDataRepository.getInstance().getListingRepository()
    }

    fun refresh() {
        CollectDataRepository.getInstance().getListingRepository()
    }

    fun removeCollectItem(id: Int, originId: Int) {
        Injection.provideCollectRemote().getWanAndroidData(id,originId,object : CollectSourceChange.BaseCallback{
            override fun success() {
                _removeSuccess.value = true
            }

            override fun failure(t: Throwable) {
                _removeSuccess.value = false
            }

        })
    }
}