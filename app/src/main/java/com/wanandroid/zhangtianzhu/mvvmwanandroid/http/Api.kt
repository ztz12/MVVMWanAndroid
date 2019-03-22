package com.wanandroid.zhangtianzhu.mvvmwanandroid.http

import retrofit2.Call
import retrofit2.http.*

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

    /**
     * 获取文章列表
     * http://www.wanandroid.com/article/list/0/json
     * @param pageNum
     */
    @GET("article/list/{pageNum}/json")
    fun getArticles(@Path("pageNum") pageNum: Int): Call<HttpResult<ArticleData>>
}