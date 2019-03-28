package com.wanandroid.zhangtianzhu.mvvmwanandroid.util

import com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.collect.CollectRemoteSource
import com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.login.LoginRemoteSource
import com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.login.RegisterRemoteSource

object Injection {
    fun provideRegisterSource():RegisterRemoteSource{
        return RegisterRemoteSource.getInstance()
    }

    fun provideLoginSource():LoginRemoteSource{
        return LoginRemoteSource.getInstance()
    }

    fun provideCollectSource():CollectRemoteSource{
        return CollectRemoteSource.getInstance()
    }
}