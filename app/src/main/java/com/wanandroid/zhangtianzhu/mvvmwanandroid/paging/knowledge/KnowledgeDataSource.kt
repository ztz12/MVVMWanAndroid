package com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.knowledge

import android.arch.paging.PageKeyedDataSource
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.HttpResult
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.KnowledgeTreeData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KnowledgeDataSource : PageKeyedDataSource<Int, KnowledgeTreeData>() {
    private var retry: (() -> Any)? = null

    fun retryAllFailed() {
        val preRetry = retry
        retry = null
        preRetry?.also {
            it.invoke()
        }
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, KnowledgeTreeData>) {
        RetrofitService.service.getKnowledgeTree()
                .enqueue(object : Callback<HttpResult<List<KnowledgeTreeData>>> {
                    override fun onFailure(call: Call<HttpResult<List<KnowledgeTreeData>>>, t: Throwable) {
                        retry = {
                            loadInitial(params, callback)
                        }
                    }

                    override fun onResponse(call: Call<HttpResult<List<KnowledgeTreeData>>>, response: Response<HttpResult<List<KnowledgeTreeData>>>) {
                        if (response.isSuccessful) {
                            val result = response.body()
                            if (result?.errorCode == 0) {
                                callback.onResult(result.data, null, 0)
                            }
                        } else {
                            retry = {
                                loadInitial(params, callback)
                            }
                        }
                    }

                })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, KnowledgeTreeData>) {
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, KnowledgeTreeData>) {
    }
}