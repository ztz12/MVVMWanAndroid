package com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.wechat

import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.databinding.FragmentWechatBinding

class WechatFragment:BaseFragment(){
    private lateinit var binding:FragmentWechatBinding
    override fun initData() {
        binding = getDataBinding() as FragmentWechatBinding
    }

    override fun getLayoutId(): Int = R.layout.fragment_wechat

    companion object {
        fun newInstance() = WechatFragment()
    }

}