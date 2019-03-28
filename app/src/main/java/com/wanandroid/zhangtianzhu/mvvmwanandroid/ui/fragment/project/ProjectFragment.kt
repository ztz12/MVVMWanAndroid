package com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.project

import android.arch.lifecycle.Observer
import android.support.design.widget.TabLayout
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.project.ProjectPageAdapter
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.databinding.FragmentProjectBinding
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.ProjectTreeData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.MainActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.project.ProjectViewModel
import kotlinx.android.synthetic.main.fragment_project.*

class ProjectFragment : BaseFragment() {
    private lateinit var binding: FragmentProjectBinding

    private var mData = mutableListOf<ProjectTreeData>()

    private lateinit var mViewModel: ProjectViewModel

    private lateinit var mAdapter: ProjectPageAdapter

    override fun getLayoutId(): Int = R.layout.fragment_project

    override fun initData() {
        binding = getDataBinding() as FragmentProjectBinding
        mViewModel = (activity as MainActivity).obtainProjectModel()
        init()
    }

    private fun init() {
        mDialog.show()
        mViewModel.getProjectData()
        binding.setLifecycleOwner(viewLifecycleOwner)
        mViewModel.projectData.observe(viewLifecycleOwner, Observer {
            if (mDialog.isShowing) {
                mDialog.dismiss()
            }
            mData = it!!
            mAdapter = ProjectPageAdapter(it, childFragmentManager)
            binding.vpProject.run {
                adapter = mAdapter
                addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.projectTabLayout))
                offscreenPageLimit = it.size
            }

            binding.projectTabLayout.run {
                setupWithViewPager(binding.vpProject)
                addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(binding.vpProject))
                addOnTabSelectedListener(onTabSelectedListener)
            }
        })
    }

    private val onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(p0: TabLayout.Tab?) {
        }

        override fun onTabSelected(p0: TabLayout.Tab?) {
            p0?.let {
                binding.vpProject.setCurrentItem(p0.position, false)
                (_mActivity as MainActivity).obtainProjectDetailModel().changeCid(mData[p0.position].id)
            }
        }

        override fun onTabUnselected(p0: TabLayout.Tab?) {
        }
    }

    fun scrollTop() {
        if (mAdapter.count == 0)
            return

        val fragment: ProjectDetailFragment = mAdapter.getItem(vp_project.currentItem) as ProjectDetailFragment
        fragment.scrollTop()
    }

    companion object {
        fun newInstance() = ProjectFragment()
    }
}