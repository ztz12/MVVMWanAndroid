package com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.home

import android.arch.lifecycle.Observer
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.home.HomeAdapter
import com.wanandroid.zhangtianzhu.mvvmwanandroid.app.WanAndroidApplication
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.databinding.FragmentHomeBinding
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.ArticleDetail
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.NetWorkUtils
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.MainActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.DialogUtil
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
        binding.setLifecycleOwner(viewLifecycleOwner)
        homeViewModel.bannerData.observe(viewLifecycleOwner, Observer { bannerData ->
            if (mDialog.isShowing) {
                mDialog.dismiss()
            }
            mAdapter = HomeAdapter(_mActivity, bannerData!!) { binding.viewmodel?.retry() }
            binding.homeRl.run {
                layoutManager = linearManager
                adapter = mAdapter
                itemAnimator = DefaultItemAnimator()
            }
            homeViewModel.homeResult.observe(viewLifecycleOwner, Observer { mAdapter.submitList(it) })
            refreshData()
            mAdapter.setOnHomeCollectListener(object : HomeAdapter.OnHomeCollectListener {
                override fun homeCollectListener(articleDetail: ArticleDetail) {
                    if (isLogin) {
                        if (!NetWorkUtils.isNetWorkAvailable(WanAndroidApplication.context)) {
                            DialogUtil.showSnackBar(_mActivity, getString(R.string.http_error))
                            return
                        }
                        val collect = articleDetail.collect
                        articleDetail.collect = !collect
                        mAdapter.notifyDataSetChanged()
                        if (collect) {
                            homeViewModel.cancelCollect(articleDetail.id)
                            DialogUtil.showSnackBar(_mActivity, getString(R.string.cancel_collect_success))
                        } else {
                            homeViewModel.collect(articleDetail.id)
                            DialogUtil.showSnackBar(_mActivity, getString(R.string.collect_success))
                        }
                    } else {
                        DialogUtil.showSnackBar(_mActivity, getString(R.string.login_tint))
                    }
                }
            })
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
        binding.homeRefresh.setOnRefreshListener {
            homeViewModel.refresh()
            setRefreshThemeColor(home_refresh)
            binding.homeRefresh.finishRefresh(1000)
        }
        binding.homeRefresh.setOnLoadMoreListener {
            homeViewModel.refresh()
            binding.homeRefresh.finishLoadMore(1000)
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

}