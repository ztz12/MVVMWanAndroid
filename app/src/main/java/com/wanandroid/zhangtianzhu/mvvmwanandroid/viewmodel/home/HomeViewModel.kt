package com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.home

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.BannerData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.HttpResult
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.RetrofitService
import com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.home.HomeDataRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel constructor(application: Application) : AndroidViewModel(application) {
    val homeResult = HomeDataRepository.getInstance().getHomeData()
    private val _bannerData = MutableLiveData<List<BannerData>>()
    val bannerData: LiveData<List<BannerData>>
        get() = _bannerData

    private val _collectSuccess = MutableLiveData<Boolean>()
    val collectSuccess: LiveData<Boolean>
        get() = _collectSuccess
    private val _cancelCollectSuccess = MutableLiveData<Boolean>()
    val cancelCollectSuccess: LiveData<Boolean>
        get() = _cancelCollectSuccess

    fun retry() {
        HomeDataRepository.getInstance().getListing()
    }

    fun refresh() {
        HomeDataRepository.getInstance().getListing()
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

    fun collect(id: Int) {
        RetrofitService.service.addCollectArticle(id).enqueue(object : Callback<HttpResult<Any>> {
            override fun onFailure(call: Call<HttpResult<Any>>, t: Throwable) {
                _collectSuccess.value = false
            }

            override fun onResponse(call: Call<HttpResult<Any>>, response: Response<HttpResult<Any>>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    _collectSuccess.value = result!!.errorCode == 0
                }
            }

        })
    }

    fun cancelCollect(id: Int) {
        RetrofitService.service.cancelCollectArticle(id).enqueue(object : Callback<HttpResult<Any>> {
            override fun onFailure(call: Call<HttpResult<Any>>, t: Throwable) {
                _cancelCollectSuccess.value = false
            }

            override fun onResponse(call: Call<HttpResult<Any>>, response: Response<HttpResult<Any>>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    _cancelCollectSuccess.value = result!!.errorCode == 0
                }
            }

        })
    }
}