package com.wanandroid.zhangtianzhu.mvvmwanandroid.paging


data class Listing <T>(
        // refreshes the whole data and fetches it from scratch.
    val refresh: () -> Unit,
        // retries any failed requests.
    val retry:()->Unit
)