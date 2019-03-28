package com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.wanandroid

interface CollectSourceChange {
    interface BaseCallback {
        fun success()
        fun failure(t: Throwable)
    }

    fun getWanAndroidData(id: Int, originId: Int, callback: BaseCallback)
}