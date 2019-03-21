package com.wanandroid.zhangtianzhu.mvvmwanandroid.bean

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.wanandroid.zhangtianzhu.mvvmwanandroid.BR

class ObserName constructor(private var name: String, private var level: String) : BaseObservable() {

    @Bindable
    fun getName():String{
        return name
    }

    fun setName(name:String){
        this.name = name
        notifyPropertyChanged(BR.name)
    }

    @Bindable
    fun getLevel():String{
        return level
    }

    fun setLevel(level: String){
        this.level = level
        //当BR.level数据改变时更新ui
        notifyPropertyChanged(BR.level)
    }

}