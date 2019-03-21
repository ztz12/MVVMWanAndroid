package com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.login

import com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.FailSource
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.LoginData

interface LoginSource {
    interface LoadLoginCallback : FailSource {
        fun getLoginData(loginData: LoginData)
    }

    fun loginWanAndroid(username: String, password: String, loadCallback: LoadLoginCallback)
}