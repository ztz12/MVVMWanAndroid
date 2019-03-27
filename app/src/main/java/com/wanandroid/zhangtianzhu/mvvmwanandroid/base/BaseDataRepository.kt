package com.wanandroid.zhangtianzhu.mvvmwanandroid.base

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.Listing

abstract class BaseDataRepository<T> {

    abstract fun getDataRepository():LiveData<PagedList<T>>

    abstract fun getListingRepository():Listing<T>
}