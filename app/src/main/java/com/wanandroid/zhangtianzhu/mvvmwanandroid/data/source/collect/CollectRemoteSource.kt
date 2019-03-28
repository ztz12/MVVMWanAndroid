package com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.collect

import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.HttpResult
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CollectRemoteSource : CollectSource {
    override fun collect(id: Int, collectCallback: CollectSource.CollectCallback) {
        RetrofitService.service.addCollectArticle(id).enqueue(object : Callback<HttpResult<Any>> {
            override fun onFailure(call: Call<HttpResult<Any>>, t: Throwable) {
                collectCallback.failure()
            }

            override fun onResponse(call: Call<HttpResult<Any>>, response: Response<HttpResult<Any>>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result?.errorCode == 0) {
                        collectCallback.success()
                    }
                }
            }

        })
    }

    override fun cancelCollect(id: Int, collectCallback: CollectSource.CollectCallback) {
        RetrofitService.service.cancelCollectArticle(id).enqueue(object : Callback<HttpResult<Any>> {
            override fun onFailure(call: Call<HttpResult<Any>>, t: Throwable) {
                collectCallback.failure()
            }

            override fun onResponse(call: Call<HttpResult<Any>>, response: Response<HttpResult<Any>>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result?.errorCode == 0) {
                        collectCallback.success()
                    }
                }
            }

        })
    }

    companion object {
        private var INSTANCE: CollectRemoteSource? = null

        fun getInstance(): CollectRemoteSource {
            return INSTANCE ?: CollectRemoteSource().apply {
                INSTANCE = this
            }
        }
    }

}