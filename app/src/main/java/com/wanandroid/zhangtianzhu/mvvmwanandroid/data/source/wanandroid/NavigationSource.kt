package com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.wanandroid

import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NavigationSource :BaseSource<MutableList<NavigationData>>{
    override fun getWanAndroidData(callback: BaseSource.BaseCallback<MutableList<NavigationData>>) {
        RetrofitService.service.getNavigationData()
                .enqueue(object : Callback<HttpResult<MutableList<NavigationData>>> {
                    override fun onFailure(call: Call<HttpResult<MutableList<NavigationData>>>, t: Throwable) {
                        callback.failure(t)
                    }

                    override fun onResponse(call: Call<HttpResult<MutableList<NavigationData>>>, response: Response<HttpResult<MutableList<NavigationData>>>) {
                        if (response.isSuccessful) {
                            val result = response.body()
                            if (result?.errorCode == 0) {
                                callback.success(result.data)
                            }
                        }
                    }

                })
    }

    companion object {
        private var INSTANCE: NavigationSource? = null

        fun getInstance(): NavigationSource {
            return INSTANCE ?: NavigationSource().apply {
                INSTANCE = this
            }
        }
    }
}