package com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.wechat

import android.arch.lifecycle.Observer
import android.support.design.widget.TabLayout
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.wechat.WeChatAdapter
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.databinding.FragmentWechatBinding
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.WeChatData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.MainActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.knowledge.KnowledgeDetailFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.wechat.WeChatViewModel
import kotlinx.android.synthetic.main.fragment_wechat.*

class WeChatFragment : BaseFragment() {
    private lateinit var binding: FragmentWechatBinding

    private var mData = mutableListOf<WeChatData>()

    private val mAdapter: WeChatAdapter by lazy { WeChatAdapter(mData,childFragmentManager) }

    private lateinit var mViewModel: WeChatViewModel

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
            binding.vpWeChat.run {
                adapter = mAdapter
                addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.weChatTabLayout))
                offscreenPageLimit = it.size
            }

            binding.weChatTabLayout.run {
                setupWithViewPager(binding.vpWeChat)
                addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(binding.vpWeChat))
                addOnTabSelectedListener(onTabSelectedListener)
            }
        })
    }

    private val onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(p0: TabLayout.Tab?) {
        }

        override fun onTabSelected(p0: TabLayout.Tab?) {
            p0?.let {
                binding.vpWeChat.setCurrentItem(p0.position, false)
                (_mActivity as MainActivity).obtainDetailModel().changeCid(mData[p0.position].id)
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