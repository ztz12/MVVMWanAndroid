package com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.project

import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.databinding.FragmentProjectBinding

class ProjectFragment:BaseFragment() {
    private lateinit var binding : FragmentProjectBinding
    override fun initData() {
        binding = getDataBinding() as FragmentProjectBinding
    }

    override fun getLayoutId(): Int = R.layout.fragment_project

    companion object {
        fun newInstance() = ProjectFragment()
    }
}