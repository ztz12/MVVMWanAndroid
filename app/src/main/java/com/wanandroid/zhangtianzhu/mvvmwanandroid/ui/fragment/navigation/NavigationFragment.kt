package com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.navigation

import android.arch.lifecycle.Observer
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.navigation.NavigationAdapter
import com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.navigation.NavigationTabAdapter
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.databinding.FragmentNavigationBinding
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.NavigationData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.MainActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.navigation.NavigationViewModel
import kotlinx.android.synthetic.main.fragment_navigation.*
import q.rorbin.verticaltablayout.VerticalTabLayout
import q.rorbin.verticaltablayout.widget.TabView

class NavigationFragment : BaseFragment() {
    private lateinit var binding: FragmentNavigationBinding

    private var mDatas = mutableListOf<NavigationData>()

    private val mAdapter: NavigationAdapter by lazy { NavigationAdapter(mDatas) }

    private val linearLayoutManager by lazy { LinearLayoutManager(_mActivity) }

    private var needScroll = false
    private var isTagClick = false
    private var mCurrentIndex = 0

    private lateinit var mViewModel: NavigationViewModel

    override fun getLayoutId(): Int = R.layout.fragment_navigation

    override fun initData() {
        binding = getDataBinding() as FragmentNavigationBinding
        mViewModel = (activity as MainActivity).obtainNavigationModel()
        init()
    }

    private fun init() {
        binding.rlNavigation.run {
            layoutManager = linearLayoutManager
            adapter = mAdapter
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(false)
        }
        mAdapter.run {
            bindToRecyclerView(binding.rlNavigation)
        }
        mDialog.show()
        mViewModel.getNavigationData()
        binding.setLifecycleOwner(viewLifecycleOwner)
        mViewModel.navigationList.observe(viewLifecycleOwner, Observer {
            if (mDialog.isShowing) {
                mDialog.dismiss()
            }
            it!!.let {
                binding.navigationVerticalTab.run {
                    setTabAdapter(NavigationTabAdapter(_mActivity, it))
                }
                mAdapter.run {
                    replaceData(it)
                }
            }
        })
        leftRightLink()
    }

    /**
     * Left TabLayout and right RecyclerView Link
     */
    private fun leftRightLink() {
        binding.rlNavigation.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (needScroll && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    scrollRecyclerView()
                }
                rightLinkLeft(newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (needScroll) {
                    scrollRecyclerView()
                }
            }
        })

        binding.navigationVerticalTab.addOnTabSelectedListener(object : VerticalTabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabView?, position: Int) {
                isTagClick = true
                selectTab(position)
            }

            override fun onTabReselected(tab: TabView?, position: Int) {

            }
        })
    }

    private fun scrollRecyclerView() {
        needScroll = false
        val indexDistance: Int = mCurrentIndex - linearLayoutManager.findFirstVisibleItemPosition()
        if (indexDistance > 0 && indexDistance < rl_navigation.childCount) {
            val top = rl_navigation.getChildAt(indexDistance).top
            rl_navigation.smoothScrollBy(0, top)
        }
    }

    /**
     * Right RecyclerView Link Left TabLayout
     */
    private fun rightLinkLeft(newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (isTagClick) {
                isTagClick = false
                return
            }
            val firstPosition = linearLayoutManager.findFirstVisibleItemPosition()
            if (firstPosition != mCurrentIndex) {
                mCurrentIndex = firstPosition
                setChecked(mCurrentIndex)
            }
        }
    }

    /**
     * Smooth Right RecyclerView to Select Left TabLayout
     */
    private fun setChecked(position: Int) {
        if (isTagClick) {
            isTagClick = false
            return
        } else {
            navigation_vertical_tab.setTabSelected(mCurrentIndex)
        }
        mCurrentIndex = position
    }

    /**
     * Select Left TabLayout to Smooth Right RecyclerView
     */
    private fun selectTab(position: Int) {
        mCurrentIndex = position
        rl_navigation.stopScroll()
        smoothScrollToPosition(position)
    }

    private fun smoothScrollToPosition(position: Int) {
        val firstPosition = linearLayoutManager.findFirstVisibleItemPosition()
        val lastPosition = linearLayoutManager.findLastVisibleItemPosition()
        when {
            position < firstPosition -> {
                rl_navigation.smoothScrollToPosition(position)
            }
            position <= lastPosition -> {
                val top = rl_navigation.getChildAt(position - firstPosition).top
                rl_navigation.smoothScrollBy(0, top)
            }
            else -> {
                rl_navigation.smoothScrollToPosition(position)
                needScroll = true
            }
        }
    }

    fun scrollTop() {
        binding.navigationVerticalTab.setTabSelected(0)
    }

    companion object {
        fun newInstance() = NavigationFragment()
    }
}