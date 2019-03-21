package com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.home

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData

class MainViewModel constructor(application: Application):AndroidViewModel(application) {
    private val currentPage = MutableLiveData<String>()


    fun changeFragmentPage(currentValue:String){
        if(currentPage.value==currentValue)
            return
        currentPage.value = currentValue
    }
}