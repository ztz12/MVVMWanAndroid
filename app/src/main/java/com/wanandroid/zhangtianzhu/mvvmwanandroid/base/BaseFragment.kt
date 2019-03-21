package com.wanandroid.zhangtianzhu.mvvmwanandroid.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.yokeyword.fragmentation.SupportFragment

abstract class BaseFragment : SupportFragment() {

    private lateinit var viewDataBinding: ViewDataBinding

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        init()
    }

    //通过DataBindingUtil.inflate加载布局，并返回加载当前binding的根布局,解决将Fragment添加到MainActivity中整体替换主页布局问题
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater,getLayoutId(),container,false)
        return viewDataBinding.root
    }
    private fun init(){
        getDataBinding()
        initData()
    }

    abstract fun getLayoutId(): Int

    abstract fun initData()

    protected fun getDataBinding(): ViewDataBinding = viewDataBinding
}