package com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.wanandroid

import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainSource :MainSourceChange{
    override fun getWanAndroidData(callback: MainSourceChange.BaseCallback) {
        RetrofitService.service.logout()
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
        private var INSTANCE: MainSource? = null

        fun getInstance(): MainSource {
            return INSTANCE ?: MainSource().apply {
                INSTANCE = this
            }
        }
    }
}