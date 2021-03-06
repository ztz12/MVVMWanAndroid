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

    /**
     * 获取轮播图
     */
    @GET("banner/json")
    fun getBannerData(): Call<HttpResult<MutableList<BannerData>>>

    /**
     * 收藏站内文章
     * http://www.wanandroid.com/lg/collect/1165/json
     * @param id article id
     */
    @POST("lg/collect/{id}/json")
    fun addCollectArticle(@Path("id") id: Int): Call<HttpResult<Any>>

    /**
     * 文章列表中取消收藏文章
     * http://www.wanandroid.com/lg/uncollect_originId/2333/json
     * @param id
     */
    @POST("lg/uncollect_originId/{id}/json")
    fun cancelCollectArticle(@Path("id") id: Int): Call<HttpResult<Any>>

    /**
     *  获取收藏列表
     *  http://www.wanandroid.com/lg/collect/list/0/json
     *  @param page
     */
    @GET("lg/collect/list/{page}/json")
    fun getCollectList(@Path("page") page: Int): Call<HttpResult<CollectionResponseBody>>

    /**
     * 我的收藏列表中取消收藏文章
     * http://www.wanandroid.com/lg/uncollect/2805/json
     * @param id
     * @param originId
     */
    @POST("lg/uncollect/{id}/json")
    @FormUrlEncoded
    fun removeCollectArticle(@Path("id") id: Int,
                             @Field("originId") originId: Int = -1): Call<HttpResult<Any>>

    /**
     * 退出登录
     * http://www.wanandroid.com/user/logout/json
     */
    @GET("user/logout/json")
    fun logout(): Call<HttpResult<Any>>

    /**
     * 知识体系
     */
    @GET("tree/json")
    fun getKnowledgeTree(): Call<HttpResult<List<KnowledgeTreeData>>>

    /**
     * 知识体系下的文章
     */
    @GET("article/list/{pageNum}/json")
    fun getKnowledgeList(@Path("pageNum") pageNum: Int,
                         @Query("cid") cid: Int): Call<HttpResult<ArticleData>>

    /**
     * 微信列表
     */
    @GET("wxarticle/chapters/json")
    fun getWeChatData(): Call<HttpResult<MutableList<WeChatData>>>

    /**
     * 导航
     */
    @GET("navi/json")
    fun getNavigationData(): Call<HttpResult<MutableList<NavigationData>>>

    /**
     * 项目分类
     */
    @GET("project/tree/json")
    fun getProjectData(): Call<HttpResult<MutableList<ProjectTreeData>>>

    /**
     * 项目列表
     */
    @GET("project/list/{pageNum}/json")
    fun getProjectListData(@Path("pageNum") pageNum: Int,
                           @Query("cid") cid: Int): Call<HttpResult<ArticleData>>
}