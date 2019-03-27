package com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.knowledge

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.KnowledgeTreeData

class KnowledgeDataSourceFactory : DataSource.Factory<Int, KnowledgeTreeData>() {
    val sourceLiveData = MutableLiveData<KnowledgeDataSource>()
    override fun create(): DataSource<Int, KnowledgeTreeData> {
        val source = KnowledgeDataSource()
        sourceLiveData.postValue(source)
        return source
    }
}