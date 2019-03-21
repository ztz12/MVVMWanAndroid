package com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.home

import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.databinding.FragmentCollectBinding

class CollectFragment:BaseFragment() {

    private lateinit var binding:FragmentCollectBinding

    override fun getLayoutId(): Int = R.layout.fragment_collect

    override fun initData() {
        binding = getDataBinding() as FragmentCollectBinding
    }

    companion object {
        fun newInstance() = CollectFragment()
    }
}