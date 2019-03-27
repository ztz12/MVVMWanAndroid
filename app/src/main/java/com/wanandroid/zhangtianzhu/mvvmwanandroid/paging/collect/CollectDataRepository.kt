package com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.collect

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseDataRepository
import com.wanandroid.zhangtianzhu.mvvmwanandroid.constant.Constants
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.CollectionArticle
import com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.Listing

class CollectDataRepository : BaseDataRepository<CollectionArticle>() {
    companion object {
        private var INSTANCE: CollectDataRepository? = null
        fun getInstance(): CollectDataRepository {
            return INSTANCE ?: CollectDataRepository().apply {
                INSTANCE = this
            }
        }
    }

    override fun getDataRepository(): LiveData<PagedList<CollectionArticle>> {
        val source = CollectDataSourceFactory()
        val config = PagedList.Config.Builder()
                .setPageSize(Constants.PAGE_SIZE)
                .setInitialLoadSizeHint(Constants.PAGE_SIZE * 2)
                .setEnablePlaceholders(Constants.ENABLE_PLACEHOLDERS)
                .build()
        return LivePagedListBuilder(source, config).build()
    }

    override fun getListingRepository(): Listing<CollectionArticle> {
        val source = CollectDataSourceFactory()
        return Listing(
                refresh = {
                    source.collectLiveData.value?.invalidate()
                },
                retry = {
                    source.collectLiveData.value?.retryAllFailed()
                }
        )
    }
}