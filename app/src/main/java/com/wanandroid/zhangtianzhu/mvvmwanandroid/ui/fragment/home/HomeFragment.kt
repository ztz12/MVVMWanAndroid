package com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.home

import android.arch.lifecycle.Observer
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.home.HomeAdapter
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.databinding.FragmentHomeBinding
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.MainActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.home.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun getLayoutId(): Int = R.layout.fragment_home

    private lateinit var binding: FragmentHomeBinding

    private val mAdapter: HomeAdapter by lazy { HomeAdapter(_mActivity) }

    override fun initData() {
        binding = getDataBinding() as FragmentHomeBinding
        homeViewModel = (activity as MainActivity).obtainHomeModel()
        init()
    }

    private fun init() {
        home_rl.run {
            layoutManager = LinearLayoutManager(_mActivity)
            adapter = mAdapter
            itemAnimator = DefaultItemAnimator()
        }

        homeViewModel.homeResult.observe(_mActivity, Observer { mAdapter.submitList(it) })
    }


    companion object {
        fun newInstance() = HomeFragment()
    }
}