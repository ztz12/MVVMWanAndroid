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

    private lateinit var mAdapter: HomeAdapter

    private val linearManager: LinearLayoutManager by lazy { LinearLayoutManager(_mActivity) }

    override fun initData() {
        binding = getDataBinding() as FragmentHomeBinding
        homeViewModel = (activity as MainActivity).obtainHomeModel()
        init()
    }

    private fun init() {
        mDialog.show()
        homeViewModel.getBannerData()
        homeViewModel.bannerData.observe(_mActivity, Observer { bannerData ->
            if(mDialog.isShowing){
                mDialog.dismiss()
            }
            mAdapter = HomeAdapter(_mActivity, bannerData!!) { binding.viewmodel?.retry() }
            home_rl.run {
                layoutManager = linearManager
                adapter = mAdapter
                itemAnimator = DefaultItemAnimator()
            }

            homeViewModel.homeResult.observe(_mActivity, Observer { mAdapter.submitList(it) })
            refreshData()
        })

    }

    fun scrollTop() {
        binding.homeRl.run {
            if (linearManager.findFirstVisibleItemPosition() > 20) {
                scrollToPosition(0)
            } else {
                smoothScrollToPosition(0)
            }
        }
    }

    private fun refreshData() {
        home_refresh.setOnRefreshListener {
            homeViewModel.refresh()
            setRefreshThemeColor(home_refresh)
            home_refresh.finishRefresh(1000)
        }
        home_refresh.setOnLoadMoreListener {
            homeViewModel.refresh()
            home_refresh.finishLoadMore(1000)
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

}