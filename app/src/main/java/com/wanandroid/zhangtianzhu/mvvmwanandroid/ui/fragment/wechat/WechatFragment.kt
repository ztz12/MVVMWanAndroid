package com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.wechat

import android.arch.lifecycle.Observer
import android.support.design.widget.TabLayout
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.wechat.WeChatAdapter
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.databinding.FragmentWechatBinding
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.WeChatData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.MainActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.knowledge.KnowledgeListActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.knowledge.KnowledgeDetailFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.wechat.WeChatViewModel
import kotlinx.android.synthetic.main.fragment_wechat.*
import kotlinx.android.synthetic.main.fragment_wechat.view.*

class WeChatFragment : BaseFragment() {
    private lateinit var binding: FragmentWechatBinding

    private lateinit var mAdapter: WeChatAdapter

    private lateinit var mViewModel: WeChatViewModel

    private var mData = mutableListOf<WeChatData>()

    override fun initData() {
        binding = getDataBinding() as FragmentWechatBinding
        mViewModel = (_mActivity as MainActivity).obtainWeChatModel()
        init()
    }

    private fun init() {
        mDialog.show()
        mViewModel.getWeChatData()
        mViewModel.weChatData.observe(_mActivity, Observer {
            if (mDialog.isShowing) {
                mDialog.dismiss()
            }
            mData = it!!
            mAdapter = WeChatAdapter(it, childFragmentManager)
            binding.vpWeChat.run {
                adapter = mAdapter
                addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(weChat_tabLayout))
                offscreenPageLimit = it.size
            }

            binding.weChatTabLayout.run {
                setupWithViewPager(vp_weChat)
                addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(vp_weChat))
                addOnTabSelectedListener(onTabSelectedListener)
            }
        })
    }

    private val onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(p0: TabLayout.Tab?) {
        }

        override fun onTabSelected(p0: TabLayout.Tab?) {
            p0?.let {
                vp_weChat.setCurrentItem(p0.position, false)
                (_mActivity as KnowledgeListActivity).obtainDetailModel().changeCid(mData[p0.position].id)
            }
        }

        override fun onTabUnselected(p0: TabLayout.Tab?) {
        }
    }

    fun scrollTop() {
        if (mAdapter.count == 0)
            return

        val fragment: KnowledgeDetailFragment = mAdapter.getItem(vp_weChat.currentItem) as KnowledgeDetailFragment
        fragment.scrollTop()
    }

    override fun getLayoutId(): Int = R.layout.fragment_wechat

    companion object {
        fun newInstance() = WeChatFragment()
    }

}