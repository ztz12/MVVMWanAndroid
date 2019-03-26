package com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.collect

import android.arch.paging.PageKeyedDataSource
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.CollectionArticle
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.CollectionResponseBody
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.HttpResult
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CollectDataSource:PageKeyedDataSource<Int,CollectionArticle>() {
    private var retry :(()->Any)?=null

    fun retryAllFailed(){
        val preRetry = retry
        retry = null
        preRetry?.also {
            it.invoke()
        }
    }
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, CollectionArticle>) {
        RetrofitService.service.getCollectList(0)
                .enqueue(object : Callback<HttpResult<CollectionResponseBody>>{
                    override fun onFailure(call: Call<HttpResult<CollectionResponseBody>>, t: Throwable) {
                        retry = {
                            loadInitial(params, callback)
                        }
                    }

                    override fun onResponse(call: Call<HttpResult<CollectionResponseBody>>, response: Response<HttpResult<CollectionResponseBody>>) {
                        if(response.isSuccessful){
                            retry = null
                            val result = response.body()
                            if(result?.errorCode == 0){
                                callback.onResult(result.data.datas,null,1)
                            }
                        }else{
                            retry = {
                                loadInitial(params, callback)
                            }
                        }
                    }

                })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, CollectionArticle>) {
        RetrofitService.service.getCollectList(params.key)
                .enqueue(object :Callback<HttpResult<CollectionResponseBody>>{
                    override fun onFailure(call: Call<HttpResult<CollectionResponseBody>>, t: Throwable) {
                        retry = {
                            loadAfter(params, callback)
                        }
                    }

                    override fun onResponse(call: Call<HttpResult<CollectionResponseBody>>, response: Response<HttpResult<CollectionResponseBody>>) {
                        if(response.isSuccessful){
                            retry = null
                            val result = response.body()
                            if(result?.errorCode == 0){
                                callback.onResult(result.data.datas,params.key+1)
                            }
                        }else{
                            retry = {
                                loadAfter(params, callback)
                            }
                        }
                    }

                })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, CollectionArticle>) {
    }
}