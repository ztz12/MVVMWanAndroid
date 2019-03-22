package com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.home

import android.arch.paging.PageKeyedDataSource
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.ArticleData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.ArticleDetail
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.HttpResult
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//PageKeyedDataSource页面在加载时插入一个上/下一个键，如从网络获取数据，会将nextPage加载到后续的加载中
class HomeDataSource : PageKeyedDataSource<Int, ArticleDetail>() {
    //该方法是接收初始第一页加载的数据，初始化PageList并对加载页计数
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ArticleDetail>) {
        RetrofitService.service.getArticles(0)
                .enqueue(object : Callback<HttpResult<ArticleData>> {
                    override fun onFailure(call: Call<HttpResult<ArticleData>>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<HttpResult<ArticleData>>, response: Response<HttpResult<ArticleData>>) {
                        if (response.isSuccessful) {
                            callback.onResult(
                                    response.body()?.data?.datas ?: emptyList(), null, 1
                            )
                        }
                    }

                })
    }

    //用于接收后一个加载的数据，指定密钥后
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ArticleDetail>) {
        RetrofitService.service.getArticles(params.key)
                .enqueue(object : Callback<HttpResult<ArticleData>> {
                    override fun onResponse(call: Call<HttpResult<ArticleData>>, response: Response<HttpResult<ArticleData>>) {
                        if (response.isSuccessful) {
                            callback.onResult(
                                    response.body()?.data?.datas ?: emptyList(), params.key + 1
                            )
                        }
                    }

                    override fun onFailure(call: Call<HttpResult<ArticleData>>, t: Throwable) {
                    }

                })
    }

    //用于接收前一个加载的数据，指定密钥前，很少使用
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ArticleDetail>) {
    }

}