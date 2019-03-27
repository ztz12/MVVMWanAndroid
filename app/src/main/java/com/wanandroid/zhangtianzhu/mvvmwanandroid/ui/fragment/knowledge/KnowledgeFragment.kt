package com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.knowledge

import android.arch.lifecycle.Observer
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.knowledge.KnowledgeAdapter
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.databinding.FragmentKnowledgeBinding
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.MainActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.knowledge.KnowledgeViewModel
import kotlinx.android.synthetic.main.fragment_knowledge.*

class KnowledgeFragment : BaseFragment() {
    private lateinit var binding: FragmentKnowledgeBinding

    private lateinit var mViewHolder: KnowledgeViewModel

    private val linearManager by lazy { LinearLayoutManager(_mActivity) }

    private lateinit var mAdapter: KnowledgeAdapter

    override fun getLayoutId(): Int = R.layout.fragment_knowledge

    override fun initData() {
        binding = getDataBinding() as FragmentKnowledgeBinding
        mViewHolder = (activity as MainActivity).obtainKnowledgeModel()
        init()
    }

    private fun init() {
        mAdapter = KnowledgeAdapter(_mActivity) { mViewHolder.retry() }
        binding.rlKnowledge.run {
            layoutManager = linearManager
            adapter = mAdapter
            itemAnimator = DefaultItemAnimator()
        }
        mViewHolder.knowledgeViewModel.observe(this, Observer {
            mAdapter.submitList(it)
        })
        refreshData()
    }

    fun scrollTop() {
        binding.rlKnowledge.run {
            if (linearManager.findFirstVisibleItemPosition() > 20) {
                scrollToPosition(0)
            } else {
                smoothScrollToPosition(0)
            }
        }
    }

    private fun refreshData() {
        binding.knowledgeRefresh.setOnRefreshListener {
            mViewHolder.refresh()
            setRefreshThemeColor(knowledge_refresh)
            binding.knowledgeRefresh.finishRefresh(1000)
        }
        binding.knowledgeRefresh.setOnLoadMoreListener {
            mViewHolder.refresh()
            binding.knowledgeRefresh.finishLoadMore(1000)
        }
    }

    companion object {
        fun newInstance() = KnowledgeFragment()
    }
}