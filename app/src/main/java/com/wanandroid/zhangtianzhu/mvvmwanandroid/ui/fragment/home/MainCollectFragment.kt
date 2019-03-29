package com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.home

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.collect.MainCollectAdapter
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.constant.Constants
import com.wanandroid.zhangtianzhu.mvvmwanandroid.databinding.FragmentCollectBinding
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.CollectionArticle
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.MainActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.home.ContentActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.DialogUtil
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.collect.CollectViewModel
import kotlinx.android.synthetic.main.fragment_collect.*

/**
 * 收藏主界面
 */
class MainCollectFragment : BaseFragment() {
    private lateinit var binding: FragmentCollectBinding

    private var mData = mutableListOf<CollectionArticle>()

    private val mAdapter: MainCollectAdapter by lazy { MainCollectAdapter(_mActivity, mData) }

    private val linearLayoutManager by lazy { LinearLayoutManager(_mActivity) }

    private lateinit var mViewModel: CollectViewModel

    override fun getLayoutId(): Int = R.layout.fragment_collect

    override fun initData() {
        binding = getDataBinding() as FragmentCollectBinding
        mViewModel = (activity as MainActivity).obtainCollectModel()
        binding.rlCollect.run {
            layoutManager = linearLayoutManager
            adapter = mAdapter
            itemAnimator = DefaultItemAnimator()
        }

        mAdapter.run {
            bindToRecyclerView(rl_collect)
            setEmptyView(R.layout.fragment_empty_layout)
            onItemClickListener = this@MainCollectFragment.onItemClickListener
            onItemChildClickListener = this@MainCollectFragment.onItemChildClickListener
        }
        init()
        refreshData()
    }

    private fun init() {
        binding.setLifecycleOwner(viewLifecycleOwner)
        mViewModel.getCollectList(0)
        mViewModel.collectionArticle.observe(viewLifecycleOwner, Observer {
            it!!.let {
                mAdapter.run {
                    replaceData(it)
                }
            }
        })
    }

    private val onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
        if (mData.size > 0) {
            val data = mData[position]
            Intent(_mActivity, ContentActivity::class.java).run {
                putExtra(Constants.CONTENT_ID_KEY, data.id)
                putExtra(Constants.CONTENT_URL_KEY, data.link)
                putExtra(Constants.CONTENT_TITLE_KEY, data.title)
                startActivity(this)
            }
        }
    }

    private val onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
        if (mData.size > 0) {
            val data = mData[position]
            when (view.id) {
                R.id.iv_collect -> {
                    //以下三个方法用来移除当前item
//                    mData.removeAt(position)
//                    mAdapter.notifyItemRemoved(position)
//                    mAdapter.notifyItemRangeChanged(position,mData.size - position)
                    mAdapter.remove(position)
                    mViewModel.removeCollectItem(data.id, data.originId)
                    mViewModel.removeSuccess.observe(viewLifecycleOwner, Observer {
                        if (it!!) {
                            DialogUtil.showSnackBar(_mActivity, getString(R.string.cancel_collect_success))
                        }
                    })
                }
            }
        }
    }

    fun scrollTop() {
        binding.rlCollect.run {
            if (linearLayoutManager.findFirstVisibleItemPosition() > 20) {
                scrollToPosition(0)
            } else {
                smoothScrollToPosition(0)
            }
        }
    }

    private fun refreshData() {
        binding.collectRefresh.setOnRefreshListener {
            setRefreshThemeColor(collect_refresh)
            mViewModel.refreshData()
            collect_refresh.finishRefresh(1000)
        }

        binding.collectRefresh.setOnLoadMoreListener {
            mViewModel.loadMore()
            collect_refresh.finishLoadMore(1000)
        }
    }

    companion object {
        fun newInstance() = MainCollectFragment()
    }
}