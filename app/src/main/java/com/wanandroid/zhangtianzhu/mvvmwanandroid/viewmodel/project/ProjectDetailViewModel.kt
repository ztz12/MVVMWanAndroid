package com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.project

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.map
import android.arch.lifecycle.Transformations.switchMap
import com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.collect.CollectSource
import com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.project.ProjectDetailRepository
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.Injection

class ProjectDetailViewModel(application: Application) : AndroidViewModel(application) {
    private var cid = MutableLiveData<Int>()

    private val projectResult = map(cid) {
        ProjectDetailRepository.getInstance().getKnowledgeDetailData(it)
    }

    val projectDetail = switchMap(projectResult) {
        it.pagedList
    }

    private val _collectSuccess = MutableLiveData<Boolean>()
    val collectSuccess: LiveData<Boolean>
        get() = _collectSuccess
    private val _cancelCollectSuccess = MutableLiveData<Boolean>()
    val cancelCollectSuccess: LiveData<Boolean>
        get() = _cancelCollectSuccess

    fun retry() {
        projectResult.value?.retry
    }

    fun refresh() {
        projectResult.value?.refresh
    }

    fun changeCid(cidValue: Int) {
        if (cid.value == cidValue)
            return
        cid.value = cidValue
    }

    fun collect(id: Int) {
        Injection.provideCollectSource().collect(id,object :CollectSource.CollectCallback{
            override fun success() {
                _collectSuccess.value = true
            }

            override fun failure() {
                _collectSuccess.value = false
            }

        })
    }

    fun cancelCollect(id: Int) {
        Injection.provideCollectSource().cancelCollect(id,object :CollectSource.CollectCallback{
            override fun success() {
                _cancelCollectSuccess.value = true
            }

            override fun failure() {
                _cancelCollectSuccess.value = false
            }

        })
    }
}