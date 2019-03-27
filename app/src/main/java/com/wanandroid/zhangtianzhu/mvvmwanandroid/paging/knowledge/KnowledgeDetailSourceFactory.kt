package com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.knowledge

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.ArticleDetail

class KnowledgeDetailSourceFactory(private val cid: Int) : DataSource.Factory<Int, ArticleDetail>() {
    val sourceLiveData = MutableLiveData<KnowledgeDetailDataSource>()
    override fun create(): DataSource<Int, ArticleDetail> {
        val source = KnowledgeDetailDataSource(cid)
        sourceLiveData.postValue(source)
        return source
    }
}