package com.wanandroid.zhangtianzhu.mvvmwanandroid.bean

import android.databinding.ObservableField

class ObserFName constructor(name: String, level: String) {
    private var name:ObservableField<String> = ObservableField()
    private var level:ObservableField<String> = ObservableField()

    init {
        this.name.set(name)
        this.level.set(level)
    }

    fun getName():ObservableField<String>{
        return name
    }

    fun setName(name:ObservableField<String>){
        this.name = name
    }

    fun getLevel():ObservableField<String>{
        return level
    }

    fun setLevel(level:ObservableField<String>){
        this.level = level
    }
}