package com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.home

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.wanandroid.MainSourceChange
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.Injection

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _loginOutSuccess = MutableLiveData<Boolean>()

    val loginOutSuccess: LiveData<Boolean>
        get() = _loginOutSuccess

    fun loginOut() {
        Injection.provideMainSource().getWanAndroidData(object :MainSourceChange.BaseCallback{
            override fun success() {
                _loginOutSuccess.value = true
            }

            override fun failure(t: Throwable) {
                _loginOutSuccess.value = false
            }

        })
    }
}