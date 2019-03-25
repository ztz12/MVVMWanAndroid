package com.wanandroid.zhangtianzhu.mvvmwanandroid.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import com.wanandroid.zhangtianzhu.mvvmwanandroid.constant.Constants
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.Preference
import me.yokeyword.fragmentation.SupportActivity

abstract class BaseActivity : SupportActivity() {
    private lateinit var viewDataBinding: ViewDataBinding

    protected var user :String by Preference(Constants.USERNAME,"")

    protected var isLogin by Preference(Constants.ISLOGIN, false)

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