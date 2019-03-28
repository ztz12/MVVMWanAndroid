package com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.wanandroid

interface MainSourceChange {
    interface BaseCallback{
        fun success()
        fun failure(t:Throwable)
    }

    fun getWanAndroidData(callback: BaseCallback)
}