package com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.home

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.BannerData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.HttpResult
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.RetrofitService
import com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.home.HomeDateRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel constructor(application: Application) : AndroidViewModel(application) {
    val homeResult = HomeDateRepository.getInstance().getHomeData()
    private val _bannerData = MutableLiveData<List<BannerData>>()
    val bannerData: LiveData<List<BannerData>>
        get() = _bannerData

    fun retry() {
        HomeDateRepository.getInstance().getListing()
    }

    fun refresh() {
        HomeDateRepository.getInstance().getListing()
    }

    fun getBannerData() {
        RetrofitService.service.getBannerData().enqueue(object : Callback<HttpResult<List<BannerData>>> {
            override fun onFailure(call: Call<HttpResult<List<BannerData>>>, t: Throwable) {

            }

            override fun onResponse(call: Call<HttpResult<List<BannerData>>>, response: Response<HttpResult<List<BannerData>>>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result!!.errorCode == 0) {
                        _bannerData.value = result.data
                    }
                }
            }
        })
    }
}