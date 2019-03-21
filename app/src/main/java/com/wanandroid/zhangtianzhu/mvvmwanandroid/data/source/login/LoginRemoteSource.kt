package com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.login

import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.HttpResult
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.LoginData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRemoteSource : LoginSource {
    override fun loginWanAndroid(username: String, password: String, loadCallback: LoginSource.LoadLoginCallback) {
        RetrofitService.service.getLoginData(username, password).enqueue(object :Callback<HttpResult<LoginData>>{
            override fun onFailure(call: Call<HttpResult<LoginData>>, t: Throwable) {
                loadCallback.showError(t)
            }

            override fun onResponse(call: Call<HttpResult<LoginData>>, response: Response<HttpResult<LoginData>>) {
                val result = response.body()
                if(result!!.errorCode==0){
                    loadCallback.getLoginData(result.data)
                }else{
                    loadCallback.showErrorMsg(result.errorMsg)
                }
            }

        })
    }

    companion object {
        private var INSTANCE:LoginRemoteSource?=null

        fun getInstance():LoginRemoteSource{
            return INSTANCE ?:LoginRemoteSource().apply {
                INSTANCE = this
            }
        }
    }
}