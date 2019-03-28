package com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.navigation

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.wanandroid.BaseSource
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.NavigationData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.Injection

class NavigationViewModel(application: Application) : AndroidViewModel(application) {
    private val _navigationList = MutableLiveData<MutableList<NavigationData>>()
    val navigationList: LiveData<MutableList<NavigationData>>
        get() = _navigationList

    fun getNavigationData() {
        Injection.provideNavigationSource().getWanAndroidData(object :BaseSource.BaseCallback<MutableList<NavigationData>>{
            override fun success(data: MutableList<NavigationData>) {
                _navigationList.value = data
            }

            override fun failure(t: Throwable) {

            }

        })
    }
}