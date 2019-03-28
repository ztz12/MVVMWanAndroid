package com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.wechat

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.wanandroid.BaseSource
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.WeChatData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.Injection

class WeChatViewModel(application: Application) : AndroidViewModel(application) {
    private val _weChatData = MutableLiveData<MutableList<WeChatData>>()
    val weChatData: LiveData<MutableList<WeChatData>>
        get() = _weChatData

    fun getWeChatData() {
        Injection.provideWeChatSource().getWanAndroidData(object :BaseSource.BaseCallback<MutableList<WeChatData>>{
            override fun success(data: MutableList<WeChatData>) {
                _weChatData.value = data
            }

            override fun failure(t: Throwable) {

            }

        })
    }

}