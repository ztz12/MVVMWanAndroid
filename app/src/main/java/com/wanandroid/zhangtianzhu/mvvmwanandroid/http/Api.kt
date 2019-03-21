package com.wanandroid.zhangtianzhu.mvvmwanandroid.http

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {
    /**
     * 登录
     */
    @POST("user/login")
    @FormUrlEncoded
    fun getLoginData(@Field("username") username: String,
                     @Field("password") password: String): Call<HttpResult<LoginData>>


    /**
     * 注册
     */
    @POST("user/register")
    @FormUrlEncoded
    fun getRegisterData(@Field("username") username: String,
                        @Field("password") password: String,
                        @Field("repassword") repassword: String): Call<HttpResult<LoginData>>
}