package com.wanandroid.zhangtianzhu.mvvmwanandroid.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import me.yokeyword.fragmentation.SupportActivity

abstract class BaseActivity : SupportActivity() {
    private lateinit var viewDataBinding: ViewDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        getDataBinding()
        initData()
        onViewSaveInstance(savedInstanceState)
    }

    abstract fun initData()

    abstract fun getLayoutId(): Int

    abstract fun onViewSaveInstance(savedInstanceState: Bundle?)

    protected fun getDataBinding(): ViewDataBinding = viewDataBinding
}