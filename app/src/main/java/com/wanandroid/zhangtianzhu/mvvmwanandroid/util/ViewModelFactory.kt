package com.wanandroid.zhangtianzhu.mvvmwanandroid.util

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.collect.CollectViewModel
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.home.HomeViewModel
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.home.MainViewModel
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.knowledge.KnowledgeDetailViewModel
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.knowledge.KnowledgeViewModel
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.login.LoginViewModel
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.login.RegisterViewModel
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.navigation.NavigationViewModel
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.project.ProjectDetailViewModel
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.project.ProjectViewModel
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.wechat.WeChatViewModel

class ViewModelFactory constructor(private val application: Application) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            with(modelClass) {
                when {
                    isAssignableFrom(RegisterViewModel::class.java) -> {
                        RegisterViewModel(application)
                    }
                    isAssignableFrom(LoginViewModel::class.java) -> {
                        LoginViewModel(application)
                    }
                    isAssignableFrom(HomeViewModel::class.java) -> {
                        HomeViewModel(application)
                    }
                    isAssignableFrom(CollectViewModel::class.java) -> {
                        CollectViewModel(application)
                    }
                    isAssignableFrom(MainViewModel::class.java) -> {
                        MainViewModel(application)
                    }
                    isAssignableFrom(KnowledgeViewModel::class.java) -> {
                        KnowledgeViewModel(application)
                    }
                    isAssignableFrom(KnowledgeDetailViewModel::class.java) ->
                        KnowledgeDetailViewModel(application)
                    isAssignableFrom(WeChatViewModel::class.java) ->
                        WeChatViewModel(application)
                    isAssignableFrom(NavigationViewModel::class.java) ->
                        NavigationViewModel(application)
                    isAssignableFrom(ProjectViewModel::class.java) ->
                        ProjectViewModel(application)
                    isAssignableFrom(ProjectDetailViewModel::class.java) ->
                        ProjectDetailViewModel(application)
                    else ->
                        throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
                }
            } as T

    companion object {
        private var INSTANCE: ViewModelFactory? = null
        fun getInstance(application: Application) =
                INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                    INSTANCE ?: ViewModelFactory(application)
                }
    }
}