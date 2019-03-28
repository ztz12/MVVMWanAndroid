package com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.project

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.ArticleDetail

class ProjectDetailSourceFactory(private val cid: Int) : DataSource.Factory<Int, ArticleDetail>() {
    val sourceLiveData = MutableLiveData<ProjectDetailDataSource>()
    override fun create(): DataSource<Int, ArticleDetail> {
        val source = ProjectDetailDataSource(cid)
        sourceLiveData.postValue(source)
        return source
    }
}