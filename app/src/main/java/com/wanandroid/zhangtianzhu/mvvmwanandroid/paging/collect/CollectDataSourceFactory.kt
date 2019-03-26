package com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.collect

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.CollectionArticle

class CollectDataSourceFactory:DataSource.Factory<Int,CollectionArticle>() {
    val collectLiveData = MutableLiveData<CollectDataSource>()
    override fun create(): DataSource<Int, CollectionArticle> {
        val sourceFactory = CollectDataSource()
        collectLiveData.postValue(sourceFactory)
        return sourceFactory
    }
}