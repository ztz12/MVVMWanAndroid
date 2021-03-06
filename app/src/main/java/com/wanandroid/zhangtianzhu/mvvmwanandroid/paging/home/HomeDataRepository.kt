package com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.home

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseDataRepository
import com.wanandroid.zhangtianzhu.mvvmwanandroid.constant.Constants
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.ArticleDetail
import com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.Listing

class HomeDataRepository : BaseDataRepository<ArticleDetail>() {
    companion object {

        private var INSTANCE: HomeDataRepository? = null

        fun getInstance(): HomeDataRepository {
            return INSTANCE ?: HomeDataRepository().apply {
                INSTANCE = this
            }
        }
    }

    override fun getDataRepository(): LiveData<PagedList<ArticleDetail>> {
        val sourceFactory = HomeDataSourceFactory()
        val config = PagedList.Config.Builder()
                .setPageSize(Constants.PAGE_SIZE)
                .setEnablePlaceholders(Constants.ENABLE_PLACEHOLDERS)//是否设置占位符，由于网络数量不固定，设置false，数量固定设置true
                .setInitialLoadSizeHint(Constants.PAGE_SIZE * 2)
                .build()
        return LivePagedListBuilder(sourceFactory, config).build()
    }

    override fun getListingRepository(): Listing<ArticleDetail> {
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