package com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.knowledge

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.knowledge.KnowledgeDetailAdapter
import com.wanandroid.zhangtianzhu.mvvmwanandroid.app.WanAndroidApplication
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.constant.Constants
import com.wanandroid.zhangtianzhu.mvvmwanandroid.databinding.FragmentKnowledgeDetailBinding
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.ArticleDetail
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.NetWorkUtils
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.knowledge.KnowledgeListActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.DialogUtil
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.initViewModel
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.knowledge.KnowledgeDetailViewModel

class KnowledgeDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentKnowledgeDetailBinding

    private var cid = 0

    private lateinit var mViewModel: KnowledgeDetailViewModel

    override fun getLayoutId(): Int = R.layout.fragment_knowledge_detail

    private lateinit var mAdapter: KnowledgeDetailAdapter

    private val linearManager: LinearLayoutManager by lazy { LinearLayoutManager(_mActivity) }

    override fun initData() {
        binding = getDataBinding() as FragmentKnowledgeDetailBinding
        cid = arguments?.getInt(Constants.CONTENT_CID_KEY) ?: 0
        mViewModel = obtainDetailModel()
        mViewModel.changeCid(cid)
        init()
    }

    private fun init() {
        mAdapter = KnowledgeDetailAdapter(context!!) { mViewModel.retry() }
        binding.rlKnowledgeDetail.run {
            layoutManager = linearManager
            adapter = mAdapter
            itemAnimator = DefaultItemAnimator()
        }
        mViewModel.knowledgeList.observe(viewLifecycleOwner, Observer {
            mAdapter.submitList(it)
        })
        mAdapter.setOnKnowledgeCollectListener(object : KnowledgeDetailAdapter.OnKnowledgeCollectListener {
            override fun knowledgeCollectListener(articleDetail: ArticleDetail) {
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
                        DialogUtil.showSnackBar(_mActivity, getString(R.string.cancel_collect_success))
                    } else {
                        mViewModel.collect(articleDetail.id)
                        DialogUtil.showSnackBar(_mActivity, getString(R.string.collect_success))
                    }
                } else {
                    DialogUtil.showSnackBar(_mActivity, getString(R.string.login_tint))
                }
            }

        })
        refreshData()
    }

    fun scrollTop() {
        binding.rlKnowledgeDetail.run {
            if (linearManager.findFirstVisibleItemPosition() > 20) {
                scrollToPosition(0)
            } else {
                smoothScrollToPosition(0)
            }
        }
    }

    private fun refreshData() {
        binding.knowledgeRefreshDetail.setOnRefreshListener {
            mViewModel.refresh()
            setRefreshThemeColor(binding.knowledgeRefreshDetail)
            binding.knowledgeRefreshDetail.finishRefresh(1000)
        }
        binding.knowledgeRefreshDetail.setOnLoadMoreListener {
            mViewModel.refresh()
            binding.knowledgeRefreshDetail.finishLoadMore(1000)
        }
    }

    companion object {
        fun getInstance(cid: Int): KnowledgeDetailFragment {
            val fragment = KnowledgeDetailFragment()
            val bundle = Bundle()
            bundle.putInt(Constants.CONTENT_CID_KEY, cid)
            fragment.arguments = bundle
            return fragment
        }
    }

    private fun obtainDetailModel() = (activity as KnowledgeListActivity).initViewModel(KnowledgeDetailViewModel::class.java)
}