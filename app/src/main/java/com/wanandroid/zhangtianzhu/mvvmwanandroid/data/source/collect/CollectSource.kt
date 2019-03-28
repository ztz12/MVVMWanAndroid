package com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.collect

interface CollectSource {
    interface CollectCallback{
        fun success()
        fun failure()
    }

    fun collect(id:Int,collectCallback: CollectCallback)

    fun cancelCollect(id:Int,collectCallback: CollectCallback)
}