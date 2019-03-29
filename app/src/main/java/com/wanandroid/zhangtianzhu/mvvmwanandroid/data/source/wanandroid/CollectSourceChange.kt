package com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.wanandroid

import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.CollectionArticle

interface CollectSourceChange {
    interface BaseCallback {
        fun success()
        fun failure(t: Throwable)
    }

    interface CollectCallback{
        fun success(data:MutableList<CollectionArticle>)
        fun failure(t: Throwable)
    }

    fun getWanAndroidData(id: Int, originId: Int, callback: BaseCallback)
    fun getCollectList(page:Int,callback: CollectCallback)
}