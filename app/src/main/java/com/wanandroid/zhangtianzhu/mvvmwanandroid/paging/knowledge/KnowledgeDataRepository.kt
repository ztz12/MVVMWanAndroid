package com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.knowledge

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseDataRepository
import com.wanandroid.zhangtianzhu.mvvmwanandroid.constant.Constants
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.KnowledgeTreeData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.Listing

class KnowledgeDataRepository : BaseDataRepository<KnowledgeTreeData>() {
    companion object {
        private var INSTANCE: KnowledgeDataRepository? = null
        fun getInstance(): KnowledgeDataRepository {
            return INSTANCE ?: KnowledgeDataRepository().apply {
                INSTANCE = this
            }
        }
    }

    override fun getDataRepository(): LiveData<PagedList<KnowledgeTreeData>> {
        val sourceFactory = KnowledgeDataSourceFactory()
        val config = PagedList.Config.Builder()
                .setPageSize(Constants.PAGE_SIZE)
                .setInitialLoadSizeHint(Constants.PAGE_SIZE * 2)
                .setEnablePlaceholders(Constants.ENABLE_PLACEHOLDERS)
                .build()
        return LivePagedListBuilder(sourceFactory, config).build()
    }

    override fun getListingRepository(): Listing<KnowledgeTreeData> {
        val sourceFactory = KnowledgeDataSourceFactory()
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