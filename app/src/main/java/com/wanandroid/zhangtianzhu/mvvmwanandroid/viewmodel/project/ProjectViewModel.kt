package com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.project

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.wanandroid.BaseSource
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.ProjectTreeData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.Injection

class ProjectViewModel(application: Application) : AndroidViewModel(application) {
    private val _projectData = MutableLiveData<MutableList<ProjectTreeData>>()
    val projectData: LiveData<MutableList<ProjectTreeData>>
        get() = _projectData

    fun getProjectData() {
        Injection.provideProjectSource().getWanAndroidData(object : BaseSource.BaseCallback<MutableList<ProjectTreeData>> {
            override fun success(data: MutableList<ProjectTreeData>) {
                _projectData.value = data
            }

            override fun failure(t: Throwable) {

            }

        })
    }
}