package com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.home

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.ArticleDetail

class HomeDateRepository {
    companion object {
        private const val PAGE_SIZE = 15
        private const val ENABLE_PLACEHOLDERS = false

        private var INSTANCE: HomeDateRepository? = null

        fun getInstance(): HomeDateRepository {
            return INSTANCE ?: HomeDateRepository().apply {
                INSTANCE = this
            }
        }
    }

    fun getHomeData(): LiveData<PagedList<ArticleDetail>> {
        val sourceFactory = HomeDataSourceFactory()
        val config = PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE)
                .setEnablePlaceholders(ENABLE_PLACEHOLDERS)//是否设置占位符，由于网络数量不固定，设置false，数量固定设置true
                .setInitialLoadSizeHint(PAGE_SIZE * 2)
                .build()
        return LivePagedListBuilder(sourceFactory, config).build()
    }
}