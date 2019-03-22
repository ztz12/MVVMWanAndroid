package com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.home

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.home.HomeDateRepository

class HomeViewModel constructor(application: Application):AndroidViewModel(application) {
    val homeResult = HomeDateRepository.getInstance().getHomeData()
}