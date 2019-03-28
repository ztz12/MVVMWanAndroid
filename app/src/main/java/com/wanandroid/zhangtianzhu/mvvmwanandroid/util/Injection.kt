package com.wanandroid.zhangtianzhu.mvvmwanandroid.util

import com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.collect.CollectRemoteSource
import com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.login.LoginRemoteSource
import com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.login.RegisterRemoteSource
import com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.wanandroid.*

object Injection {
    fun provideRegisterSource(): RegisterRemoteSource {
        return RegisterRemoteSource.getInstance()
    }

    fun provideLoginSource(): LoginRemoteSource {
        return LoginRemoteSource.getInstance()
    }

    fun provideCollectSource(): CollectRemoteSource {
        return CollectRemoteSource.getInstance()
    }

    fun provideProjectSource(): ProjectSource {
        return ProjectSource.getInstance()
    }

    fun provideWeChatSource(): WechatSource {
        return WechatSource.getInstance()
    }

    fun provideHomeSource(): HomeSource {
        return HomeSource.getInstance()
    }

    fun provideNavigationSource(): NavigationSource {
        return NavigationSource.getInstance()
    }

    fun provideMainSource(): MainSource {
        return MainSource.getInstance()
    }

    fun provideCollectRemote(): CollectRemote {
        return CollectRemote.getInstance()
    }
}