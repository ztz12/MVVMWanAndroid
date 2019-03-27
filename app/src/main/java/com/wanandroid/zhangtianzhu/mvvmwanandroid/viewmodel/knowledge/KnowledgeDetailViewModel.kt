package com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.knowledge

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.map
import android.arch.lifecycle.Transformations.switchMap
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.HttpResult
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.RetrofitService
import com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.knowledge.KnowledgeDetailRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KnowledgeDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val cid = MutableLiveData<Int>()
    val knowledgeDetailResult = map(cid) {
        KnowledgeDetailRepository.getInstance().getKnowledgeDetailData(it)
    }
    val knowledgeList = switchMap(knowledgeDetailResult) {
        it.pagedList
    }
    private val _collectSuccess = MutableLiveData<Boolean>()
    val collectSuccess: LiveData<Boolean>
        get() = _collectSuccess
    private val _cancelCollectSuccess = MutableLiveData<Boolean>()
    val cancelCollectSuccess: LiveData<Boolean>
        get() = _cancelCollectSuccess

    fun retry() {
        knowledgeDetailResult.value?.retry
    }

    fun refresh() {
        knowledgeDetailResult.value?.refresh
    }

    fun changeCid(cidValue: Int) {
        if (cid.value == cidValue)
            return
        cid.value = cidValue
    }

    fun collect(id: Int) {
        RetrofitService.service.addCollectArticle(id).enqueue(object : Callback<HttpResult<Any>> {
            override fun onFailure(call: Call<HttpResult<Any>>, t: Throwable) {
                _collectSuccess.value = false
            }

            override fun onResponse(call: Call<HttpResult<Any>>, response: Response<HttpResult<Any>>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    _collectSuccess.value = result!!.errorCode == 0
                }
            }

        })
    }

    fun cancelCollect(id: Int) {
        RetrofitService.service.cancelCollectArticle(id).enqueue(object : Callback<HttpResult<Any>> {
            override fun onFailure(call: Call<HttpResult<Any>>, t: Throwable) {
                _cancelCollectSuccess.value = false
            }

            override fun onResponse(call: Call<HttpResult<Any>>, response: Response<HttpResult<Any>>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    _cancelCollectSuccess.value = result!!.errorCode == 0
                }
            }

        })
    }
}