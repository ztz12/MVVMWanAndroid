package com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.login

import com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.FailSource
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.LoginData

interface RegisterSource {
    interface LoadRegisterCallback : FailSource {
        fun getRegisterData(loginData: LoginData)
    }

    fun registerWanAndroid(username: String, password: String, repassword: String, loadCallback: LoadRegisterCallback)
}