package com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.navigation

import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.databinding.FragmentNavigationBinding

class NavigationFragment:BaseFragment() {
    private lateinit var binding:FragmentNavigationBinding
    override fun initData() {
        binding = getDataBinding() as FragmentNavigationBinding
    }

    override fun getLayoutId(): Int = R.layout.fragment_navigation

    companion object {
        fun newInstance() = NavigationFragment()
    }
}