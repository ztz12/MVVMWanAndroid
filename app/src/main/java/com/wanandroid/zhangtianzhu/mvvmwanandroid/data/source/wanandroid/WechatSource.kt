package com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.wanandroid

import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.HttpResult
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.ProjectTreeData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.RetrofitService
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.WeChatData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WechatSource :BaseSource<MutableList<WeChatData>>{
    override fun getWanAndroidData(callback: BaseSource.BaseCallback<MutableList<WeChatData>>) {
        RetrofitService.service.getWeChatData()
                .enqueue(object : Callback<HttpResult<MutableList<WeChatData>>> {
                    override fun onFailure(call: Call<HttpResult<MutableList<WeChatData>>>, t: Throwable) {
                        callback.failure(t)
                    }

                    override fun onResponse(call: Call<HttpResult<MutableList<WeChatData>>>, response: Response<HttpResult<MutableList<WeChatData>>>) {
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
        private var INSTANCE: WechatSource? = null

        fun getInstance(): WechatSource {
            return INSTANCE ?: WechatSource().apply {
                INSTANCE = this
            }
        }
    }
}