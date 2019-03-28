package com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.project

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.HttpResult
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.ProjectTreeData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.RetrofitService
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.WeChatData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectViewModel(application: Application) : AndroidViewModel(application) {
    private val _projectData = MutableLiveData<MutableList<ProjectTreeData>>()
    val projectData: LiveData<MutableList<ProjectTreeData>>
        get() = _projectData

    fun getProjectData() {
        RetrofitService.service.getProjectData()
                .enqueue(object : Callback<HttpResult<MutableList<ProjectTreeData>>> {
                    override fun onFailure(call: Call<HttpResult<MutableList<ProjectTreeData>>>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<HttpResult<MutableList<ProjectTreeData>>>, response: Response<HttpResult<MutableList<ProjectTreeData>>>) {
                        if (response.isSuccessful) {
                            val result = response.body()
                            if (result?.errorCode == 0) {
                                _projectData.value = result.data
                            }
                        }
                    }

                })
    }
}