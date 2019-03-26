package com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.collect

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.HttpResult
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.RetrofitService
import com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.collect.CollectDataRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CollectViewModel constructor(application: Application) : AndroidViewModel(application) {
    val collectViewModel = CollectDataRepository.getInstance().getCollectData()
    private val _removeSuccess = MutableLiveData<Boolean>()
    val removeSuccess: LiveData<Boolean>
        get() = _removeSuccess

    fun retry() {
        CollectDataRepository.getInstance().getCollectListing()
    }

    fun refresh() {
        CollectDataRepository.getInstance().getCollectListing()
    }

    fun removeCollectItem(id: Int, originId: Int) {
        RetrofitService.service.removeCollectArticle(id, originId)
                .enqueue(object : Callback<HttpResult<Any>> {
                    override fun onFailure(call: Call<HttpResult<Any>>, t: Throwable) {
                        _removeSuccess.value = false
                    }

                    override fun onResponse(call: Call<HttpResult<Any>>, response: Response<HttpResult<Any>>) {
                        if (response.isSuccessful) {
                            val result = response.body()
                            if (result?.errorCode == 0) {
                                _removeSuccess.value = true
                            }
                        }
                    }

                })
    }
}