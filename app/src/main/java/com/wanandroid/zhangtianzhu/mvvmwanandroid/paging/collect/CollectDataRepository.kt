package com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.collect

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.CollectionArticle
import com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.Listing

class CollectDataRepository {
    companion object {
        private const val PAGE_SIZE = 15
        private const val ENABLE_PLACEHOLDERS = false

        private var INSTANCE: CollectDataRepository? = null
        fun getInstance(): CollectDataRepository {
            return INSTANCE ?: CollectDataRepository().apply {
                INSTANCE = this
            }
        }
    }

    fun getCollectData(): LiveData<PagedList<CollectionArticle>> {
        val source = CollectDataSourceFactory()
        val config = PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE)
                .setInitialLoadSizeHint(PAGE_SIZE * 2)
                .setEnablePlaceholders(ENABLE_PLACEHOLDERS)
                .build()
        return LivePagedListBuilder(source, config).build()
    }

    fun getCollectListing(): Listing<CollectionArticle> {
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