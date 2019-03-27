package com.wanandroid.zhangtianzhu.mvvmwanandroid.paging

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList

data class ListingData<T>(
        // the LiveData of paged lists for the UI to observe
        val pagedList: LiveData<PagedList<T>>,
        // refreshes the whole data and fetches it from scratch.
        val refresh: () -> Unit,
        // retries any failed requests.
        val retry: () -> Unit)
