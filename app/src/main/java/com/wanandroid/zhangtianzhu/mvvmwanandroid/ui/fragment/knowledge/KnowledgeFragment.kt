package com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.knowledge

import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.databinding.FragmentKnowledgeBinding

class KnowledgeFragment:BaseFragment() {
    private lateinit var binding:FragmentKnowledgeBinding
    override fun initData() {
        binding = getDataBinding() as FragmentKnowledgeBinding
    }

    override fun getLayoutId(): Int = R.layout.fragment_knowledge

    companion object {
        fun newInstance() = KnowledgeFragment()
    }
}