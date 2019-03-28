package com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.home

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.collect.CollectSource
import com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.wanandroid.BaseSource
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.BannerData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.home.HomeDataRepository
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.Injection

class HomeViewModel constructor(application: Application) : AndroidViewModel(application) {
    val homeResult = HomeDataRepository.getInstance().getDataRepository()
    private val _bannerData = MutableLiveData<MutableList<BannerData>>()
    val bannerData: LiveData<MutableList<BannerData>>
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
        Injection.provideHomeSource().getWanAndroidData(object :BaseSource.BaseCallback<MutableList<BannerData>>{
            override fun success(data: MutableList<BannerData>) {
                _bannerData.value = data
            }

            override fun failure(t: Throwable) {
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