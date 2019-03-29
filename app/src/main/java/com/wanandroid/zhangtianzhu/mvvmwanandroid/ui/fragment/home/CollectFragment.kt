package com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.home

import android.arch.lifecycle.Observer
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.collect.CollectAdapter
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.databinding.FragmentCollectBinding
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.CollectionArticle
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.MainActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.DialogUtil
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.collect.CollectViewModel
import kotlinx.android.synthetic.main.fragment_collect.*

@Deprecated("取消该收藏Fragment，换成MainCollectFragment")
class CollectFragment : BaseFragment() {

    private lateinit var binding: FragmentCollectBinding

    private lateinit var viewModel: CollectViewModel

    private lateinit var mAdapter: CollectAdapter

    private val linearManager: LinearLayoutManager by lazy { LinearLayoutManager(_mActivity) }

    override fun getLayoutId(): Int = R.layout.fragment_collect

    override fun initData() {
        binding = getDataBinding() as FragmentCollectBinding
        viewModel = (activity as MainActivity).obtainCollectModel()
        init()
    }

    private fun init() {
        mAdapter = CollectAdapter(_mActivity) { viewModel.retry() }
        binding.setLifecycleOwner(viewLifecycleOwner)
        binding.rlCollect.run {
            layoutManager = linearManager
            adapter = mAdapter
            itemAnimator = DefaultItemAnimator()
        }
        viewModel.collectViewModel.observe(viewLifecycleOwner, Observer {
            mAdapter.submitList(it)
        })
        refreshData()
        mAdapter.setOnCollectListener(object : CollectAdapter.OnCollectAdapterListener {
            override fun collectListener(collectionArticle: CollectionArticle, position: Int,list: MutableList<CollectionArticle>) {
                viewModel.removeCollectItem(collectionArticle.id, collectionArticle.originId)
                //notifyItemRemoved只是移除动画，并没有真正移除当前item，没有调用onBindViewHolder重新绑定数据，需要调用notifyItemRangeChanged，
                //从当前位置到其后range范围内进行重新onBindViewHolder绑定数据
                list.removeAt(position)
                mAdapter.notifyItemRemoved(position)
                mAdapter.notifyItemRangeChanged(position,list.size - position)
                viewModel.removeSuccess.observe(viewLifecycleOwner, Observer {
                    if (it!!) {
                        DialogUtil.showSnackBar(_mActivity, getString(R.string.cancel_collect_success))
                    }
                })
            }

        })
    }

    fun scrollTop() {
        binding.rlCollect.run {
            if (linearManager.findFirstVisibleItemPosition() > 20) {
                scrollToPosition(0)
            } else {
                smoothScrollToPosition(0)
            }
        }
    }

    private fun refreshData() {
        binding.collectRefresh.setOnRefreshListener {
            viewModel.refresh()
            setRefreshThemeColor(collect_refresh)
            binding.collectRefresh.finishRefresh(1000)
        }
        binding.collectRefresh.setOnLoadMoreListener {
            viewModel.refresh()
            binding.collectRefresh.finishLoadMore(1000)
        }
    }

    companion object {
        fun newInstance() = CollectFragment()
    }
}