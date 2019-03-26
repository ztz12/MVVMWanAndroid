package com.wanandroid.zhangtianzhu.mvvmwanandroid.util

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.collect.CollectViewModel
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.home.HomeViewModel
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.home.MainViewModel
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.login.LoginViewModel
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.login.RegisterViewModel

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
                    isAssignableFrom(HomeViewModel::class.java) ->{
                        HomeViewModel(application)
                    }
                    isAssignableFrom(CollectViewModel::class.java) ->{
                        CollectViewModel(application)
                    }
                    isAssignableFrom(MainViewModel::class.java) ->{
                        MainViewModel(application)
                    }
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