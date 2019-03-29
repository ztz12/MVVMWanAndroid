package com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.wanandroid

import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.CollectionResponseBody
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.HttpResult
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CollectRemote : CollectSourceChange {
    override fun getCollectList(page: Int, callback: CollectSourceChange.CollectCallback) {
        RetrofitService.service.getCollectList(page)
                .enqueue(object :Callback<HttpResult<CollectionResponseBody>>{
                    override fun onFailure(call: Call<HttpResult<CollectionResponseBody>>, t: Throwable) {
                        callback.failure(t)
                    }

                    override fun onResponse(call: Call<HttpResult<CollectionResponseBody>>, response: Response<HttpResult<CollectionResponseBody>>) {
                        if(response.isSuccessful){
                            val result = response.body()
                            if(result?.errorCode == 0){
                                callback.success(result.data.datas)
                            }
                        }
                    }

                })
    }

    override fun getWanAndroidData(id: Int, originId: Int, callback: CollectSourceChange.BaseCallback) {
        RetrofitService.service.removeCollectArticle(id, originId)
                .enqueue(object : Callback<HttpResult<Any>> {
                    override fun onFailure(call: Call<HttpResult<Any>>, t: Throwable) {
                        callback.failure(t)
                    }

                    override fun onResponse(call: Call<HttpResult<Any>>, response: Response<HttpResult<Any>>) {
                        if (response.isSuccessful) {
                            val result = response.body()
                            if (result?.errorCode == 0) {
                                callback.success()
                            }
                        }
                    }

                })
    }

    companion object {
        private var INSTANCE: CollectRemote? = null

        fun getInstance(): CollectRemote {
            return INSTANCE ?: CollectRemote().apply {
                INSTANCE = this
            }
        }
    }

}