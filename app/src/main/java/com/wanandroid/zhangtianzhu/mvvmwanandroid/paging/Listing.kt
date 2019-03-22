package com.wanandroid.zhangtianzhu.mvvmwanandroid.paging

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList

data class Listing <T>(
    val pagedList:LiveData<PagedList<T>>,
    val networkState: LiveData<NetworkState>,
        // represents the refresh status to show to the user. Separate from networkState, this
        // value is importantly only when refresh is requested.
    val refreshState: LiveData<NetworkState>,
        // refreshes the whole data and fetches it from scratch.
    val refresh: () -> Unit,
        // retries any failed requests.
    val retry: () -> Unit)
