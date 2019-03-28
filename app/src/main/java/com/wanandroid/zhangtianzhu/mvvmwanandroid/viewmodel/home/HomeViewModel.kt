package com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.home

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.collect.CollectSource
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.BannerData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.HttpResult
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.RetrofitService
import com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.home.HomeDataRepository
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.Injection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel constructor(application: Application) : AndroidViewModel(application) {
    val homeResult = HomeDataRepository.getInstance().getDataRepository()
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
        HomeDataRepository.getInstance().getListingRepository()
    }

    fun refresh() {
        HomeDataRepository.getInstance().getListingRepository()
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
        Injection.provideCollectSource().collect(id,object : CollectSource.CollectCallback{
            override fun success() {
                _collectSuccess.value = true
            }

            override fun failure() {
                _collectSuccess.value = false
            }

        })
    }

    fun cancelCollect(id: Int) {
        Injection.provideCollectSource().cancelCollect(id,object : CollectSource.CollectCallback{
            override fun success() {
                _cancelCollectSuccess.value = true
            }

            override fun failure() {
                _cancelCollectSuccess.value = false
            }

        })
    }
}