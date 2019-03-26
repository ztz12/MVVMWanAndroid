package com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.home

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.ArticleDetail
import com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.Listing

class HomeDataRepository {
    companion object {
        private const val PAGE_SIZE = 15
        private const val ENABLE_PLACEHOLDERS = false

        private var INSTANCE: HomeDataRepository? = null

        fun getInstance(): HomeDataRepository {
            return INSTANCE ?: HomeDataRepository().apply {
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

    fun getListing(): Listing<ArticleDetail> {
        val sourceFactory = HomeDataSourceFactory()
        return Listing(
                retry = {
                    sourceFactory.sourceLiveData.value?.retryAllFailed()
                },
                refresh = {
                    sourceFactory.sourceLiveData.value?.invalidate()
                }
        )
    }
}