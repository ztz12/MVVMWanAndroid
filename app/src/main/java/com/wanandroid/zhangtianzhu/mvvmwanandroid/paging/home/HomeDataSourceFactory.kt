package com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.home

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.ArticleDetail

class HomeDataSourceFactory:DataSource.Factory<Int,ArticleDetail>() {
    val sourceLiveData = MutableLiveData<HomeDataSource>()
    override fun create(): DataSource<Int, ArticleDetail> {
        val source = HomeDataSource()
        sourceLiveData.postValue(source)
        return source
    }
}