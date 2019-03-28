package com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.project

import android.arch.paging.PageKeyedDataSource
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectDetailDataSource(private val cid: Int) : PageKeyedDataSource<Int, ArticleDetail>() {
    private var retry: (() -> Any)? = null

    fun retryAllFailed() {
        val preRetry = retry
        retry = null
        preRetry?.also {
            it.invoke()
        }
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ArticleDetail>) {
        RetrofitService.service.getProjectListData(0, cid)
                .enqueue(object : Callback<HttpResult<ArticleData>> {
                    override fun onFailure(call: Call<HttpResult<ArticleData>>, t: Throwable) {
                        retry = {
                            loadInitial(params, callback)
                        }
                    }

                    override fun onResponse(call: Call<HttpResult<ArticleData>>, response: Response<HttpResult<ArticleData>>) {
                        if (response.isSuccessful) {
                            retry = null
                            val result = response.body()
                            if (result?.errorCode == 0) {
                                callback.onResult(
                                        result.data.datas, null, 1
                                )
                            }
                        } else {
                            retry = {
                                loadInitial(params, callback)
                            }
                        }
                    }

                })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ArticleDetail>) {
        RetrofitService.service.getProjectListData(params.key, cid)
                .enqueue(object : Callback<HttpResult<ArticleData>> {
                    override fun onFailure(call: Call<HttpResult<ArticleData>>, t: Throwable) {
                        retry = {
                            loadAfter(params, callback)
                        }
                    }

                    override fun onResponse(call: Call<HttpResult<ArticleData>>, response: Response<HttpResult<ArticleData>>) {
                        if (response.isSuccessful) {
                            retry = null
                            val result = response.body()
                            if (result?.errorCode == 0) {
                                callback.onResult(
                                        result.data.datas, params.key + 1
                                )
                            }
                        } else {
                            retry = {
                                loadAfter(params, callback)
                            }
                        }
                    }

                })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ArticleDetail>) {
    }


}