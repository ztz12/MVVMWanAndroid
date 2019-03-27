package com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.knowledge

import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.wanandroid.zhangtianzhu.mvvmwanandroid.constant.Constants
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.ArticleDetail
import com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.ListingData


class KnowledgeDetailRepository {

    companion object {
        private var INSTANCE: KnowledgeDetailRepository? = null

        fun getInstance(): KnowledgeDetailRepository {
            return INSTANCE ?: KnowledgeDetailRepository().apply {
                INSTANCE = this
            }
        }
    }

    fun getKnowledgeDetailData(cid: Int): ListingData<ArticleDetail> {
        val source = KnowledgeDetailSourceFactory(cid)
        val config = PagedList.Config.Builder()
                .setPageSize(Constants.PAGE_SIZE)
                .setInitialLoadSizeHint(Constants.PAGE_SIZE * 2)
                .setEnablePlaceholders(Constants.ENABLE_PLACEHOLDERS)
                .build()
        val pageList = LivePagedListBuilder(source, config).build()
        return ListingData(
                refresh = {
                    source.sourceLiveData.value?.invalidate()
                },
                retry = {
                    source.sourceLiveData.value?.retryAllFailed()
                },
                pagedList = pageList
        )
    }

}