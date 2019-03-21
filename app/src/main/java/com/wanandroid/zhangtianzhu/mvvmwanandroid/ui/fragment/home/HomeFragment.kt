package com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.home

import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.databinding.FragmentHomeBinding

class HomeFragment:BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_home

    private lateinit var binding:FragmentHomeBinding

    override fun initData() {
        binding = getDataBinding() as FragmentHomeBinding
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}