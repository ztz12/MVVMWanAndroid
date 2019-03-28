package com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.navigation

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.HttpResult
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.NavigationData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NavigationViewModel(application: Application) : AndroidViewModel(application) {
    private val _navigationList = MutableLiveData<MutableList<NavigationData>>()
    val navigationList: LiveData<MutableList<NavigationData>>
        get() = _navigationList

    fun getNavigationData() {
        RetrofitService.service.getNavigationData()
                .enqueue(object : Callback<HttpResult<MutableList<NavigationData>>> {
                    override fun onFailure(call: Call<HttpResult<MutableList<NavigationData>>>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<HttpResult<MutableList<NavigationData>>>, response: Response<HttpResult<MutableList<NavigationData>>>) {
                        if (response.isSuccessful) {
                            val result = response.body()
                            if (result?.errorCode == 0) {
                                _navigationList.value = result.data
                            }
                        }
                    }

                })
    }
}