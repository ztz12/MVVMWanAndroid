package com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.login

import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.HttpResult
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.LoginData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterRemoteSource :RegisterSource {

    override fun registerWanAndroid(username: String, password: String, repassword: String,loadCallback: RegisterSource.LoadRegisterCallback) {
        RetrofitService.service.getRegisterData(username, password, repassword).enqueue(object :Callback<HttpResult<LoginData>>{
            override fun onFailure(call: Call<HttpResult<LoginData>>, t: Throwable) {
                loadCallback.showError(t)
            }

            override fun onResponse(call: Call<HttpResult<LoginData>>, response: Response<HttpResult<LoginData>>) {
                if(response.isSuccessful){
                    val registerResult = response.body()
                    if(registerResult?.errorCode==0){
                        loadCallback.getRegisterData(registerResult.data)
                    }else{
                        loadCallback.showErrorMsg(registerResult!!.errorMsg)
                    }
                }
            }
        })
    }

    companion object {
        private var INSTANCE:RegisterRemoteSource?=null

        fun getInstance():RegisterRemoteSource{
            return INSTANCE ?: RegisterRemoteSource().apply {
                INSTANCE = this
            }
        }
    }
}