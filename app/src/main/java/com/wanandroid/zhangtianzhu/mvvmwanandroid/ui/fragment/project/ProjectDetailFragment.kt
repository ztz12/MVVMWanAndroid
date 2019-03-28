package com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.project

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.project.ProjectDetailAdapter
import com.wanandroid.zhangtianzhu.mvvmwanandroid.app.WanAndroidApplication
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.constant.Constants
import com.wanandroid.zhangtianzhu.mvvmwanandroid.databinding.FragmentProjectDetailBinding
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.ArticleDetail
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.NetWorkUtils
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.MainActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.DialogUtil
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.project.ProjectDetailViewModel

class ProjectDetailFragment : BaseFragment() {

    private var cid = 0

    private lateinit var mViewModel: ProjectDetailViewModel

    private lateinit var binding: FragmentProjectDetailBinding

    private lateinit var mAdapter: ProjectDetailAdapter

    private val linearLayoutManager by lazy { LinearLayoutManager(_mActivity) }

    companion object {
        fun getInstance(cid: Int): ProjectDetailFragment {
            val fragment = ProjectDetailFragment()
            val bundle = Bundle()
            bundle.putInt(Constants.CONTENT_CID_KEY, cid)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_project_detail

    override fun initData() {
        binding = getDataBinding() as FragmentProjectDetailBinding
        cid = arguments?.getInt(Constants.CONTENT_CID_KEY) ?: 0
        mViewModel = (activity as MainActivity).obtainProjectDetailModel()
        mViewModel.changeCid(cid)
        init()
    }

    private fun init() {
        mAdapter = ProjectDetailAdapter(context!!) { mViewModel.retry() }
        binding.setLifecycleOwner(viewLifecycleOwner)
        binding.rlProjectList.run {
            layoutManager = linearLayoutManager
            adapter = mAdapter
            itemAnimator = DefaultItemAnimator()
        }
        mViewModel.projectDetail.observe(viewLifecycleOwner, Observer {
            mAdapter.submitList(it)
        })
        mAdapter.setOnProjectCollectListener(object : ProjectDetailAdapter.OnProjectCollectListener {
            override fun projectCollectListener(articleDetail: ArticleDetail) {
                if (isLogin) {
                    if (!NetWorkUtils.isNetWorkAvailable(WanAndroidApplication.context)) {
                        DialogUtil.showSnackBar(_mActivity, getString(R.string.http_error))
                        return
                    }
                    val collect = articleDetail.collect
                    articleDetail.collect = !collect
                    mAdapter.notifyDataSetChanged()
                    if (collect) {
                        mViewModel.cancelCollect(articleDetail.id)
                        mViewModel.cancelCollectSuccess.observe(viewLifecycleOwner, Observer {
                            if(it!!){
                                DialogUtil.showSnackBar(_mActivity, getString(R.string.cancel_collect_success))
                            }
                        })
                    } else {
                        mViewModel.collect(articleDetail.id)
                        mViewModel.collectSuccess.observe(viewLifecycleOwner, Observer {
                            if(it!!){
                                DialogUtil.showSnackBar(_mActivity, getString(R.string.collect_success))
                            }
                        })
                    }
                } else {
                    DialogUtil.showSnackBar(_mActivity, getString(R.string.login_tint))
                }
            }
        })
        refreshData()
    }

    private fun refreshData() {
        binding.projectRefresh.setOnRefreshListener {
            mViewModel.refresh()
            setRefreshThemeColor(binding.projectRefresh)
            binding.projectRefresh.finishRefresh(1000)
        }
        binding.projectRefresh.setOnLoadMoreListener {
            mViewModel.refresh()
            binding.projectRefresh.finishLoadMore(1000)
        }
    }

    fun scrollTop() {
        binding.rlProjectList.run {
            if (linearLayoutManager.findFirstVisibleItemPosition() > 20) {
                scrollToPosition(0)
            } else {
                smoothScrollToPosition(0)
            }
        }
    }
}