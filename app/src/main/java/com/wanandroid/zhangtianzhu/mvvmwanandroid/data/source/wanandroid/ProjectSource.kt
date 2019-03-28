package com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.wanandroid

import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.HttpResult
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.ProjectTreeData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectSource :BaseSource<MutableList<ProjectTreeData>>{
    override fun getWanAndroidData(callback: BaseSource.BaseCallback<MutableList<ProjectTreeData>>) {
        RetrofitService.service.getProjectData()
                .enqueue(object : Callback<HttpResult<MutableList<ProjectTreeData>>> {
                    override fun onFailure(call: Call<HttpResult<MutableList<ProjectTreeData>>>, t: Throwable) {
                        callback.failure(t)
                    }

                    override fun onResponse(call: Call<HttpResult<MutableList<ProjectTreeData>>>, response: Response<HttpResult<MutableList<ProjectTreeData>>>) {
                        if (response.isSuccessful) {
                            val result = response.body()
                            if (result?.errorCode == 0) {
                                callback.success(result.data)
                            }
                        }
                    }

                })
    }

    companion object {
        private var INSTANCE: ProjectSource? = null

        fun getInstance(): ProjectSource {
            return INSTANCE ?: ProjectSource().apply {
                INSTANCE = this
            }
        }
    }
}