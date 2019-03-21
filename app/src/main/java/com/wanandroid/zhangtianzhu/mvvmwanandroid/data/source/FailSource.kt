package com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source

interface FailSource {
    fun showErrorMsg(msg:String)
    fun showError(t:Throwable)
}