package com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.wanandroid

import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeSource :BaseSource<MutableList<BannerData>>{
    override fun getWanAndroidData(callback: BaseSource.BaseCallback<MutableList<BannerData>>) {
        RetrofitService.service.getBannerData()
                .enqueue(object : Callback<HttpResult<MutableList<BannerData>>> {
                    override fun onFailure(call: Call<HttpResult<MutableList<BannerData>>>, t: Throwable) {
                        callback.failure(t)
                    }

                    override fun onResponse(call: Call<HttpResult<MutableList<BannerData>>>, response: Response<HttpResult<MutableList<BannerData>>>) {
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
        private var INSTANCE: HomeSource? = null

        fun getInstance(): HomeSource {
            return INSTANCE ?: HomeSource().apply {
                INSTANCE = this
            }
        }
    }
}