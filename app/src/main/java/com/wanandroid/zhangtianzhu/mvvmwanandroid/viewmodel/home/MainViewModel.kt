package com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.home

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.HttpResult
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _loginOutSuccess = MutableLiveData<Boolean>()

    val loginOutSuccess: LiveData<Boolean>
        get() = _loginOutSuccess

    fun loginOut() {
        RetrofitService.service.logout()
                .enqueue(object : Callback<HttpResult<Any>> {
                    override fun onFailure(call: Call<HttpResult<Any>>, t: Throwable) {
                        _loginOutSuccess.value = false
                    }

                    override fun onResponse(call: Call<HttpResult<Any>>, response: Response<HttpResult<Any>>) {
                        if (response.isSuccessful) {
                            val result = response.body()
                            _loginOutSuccess.value = result?.errorCode == 0
                        }
                    }

                })
    }
}