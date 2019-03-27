package com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.wechat

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.HttpResult
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.RetrofitService
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.WeChatData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeChatViewModel(application: Application) : AndroidViewModel(application) {
    private val _weChatData = MutableLiveData<MutableList<WeChatData>>()
    val weChatData: LiveData<MutableList<WeChatData>>
        get() = _weChatData

    fun getWeChatData() {
        RetrofitService.service.getWeChatData()
                .enqueue(object : Callback<HttpResult<MutableList<WeChatData>>> {
                    override fun onFailure(call: Call<HttpResult<MutableList<WeChatData>>>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<HttpResult<MutableList<WeChatData>>>, response: Response<HttpResult<MutableList<WeChatData>>>) {
                        if (response.isSuccessful) {
                            val result = response.body()
                            if (result?.errorCode == 0) {
                                _weChatData.value = result.data
                            }
                        }
                    }

                })
    }

}