package com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.wanandroid

interface BaseSource<T> {
    interface BaseCallback<T>{
        fun success(data:T)
        fun failure(t:Throwable)
    }

    fun getWanAndroidData(callback: BaseCallback<T>)
}