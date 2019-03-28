package com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.project

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.text.Html
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.ProjectTreeData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.project.ProjectDetailFragment

class ProjectPageAdapter(val list: MutableList<ProjectTreeData>, fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    private val fragments = mutableListOf<Fragment>()

    init {
        fragments.clear()
        list.forEach {
            fragments.add(ProjectDetailFragment.getInstance(it.id))
        }
    }

    override fun getItem(p0: Int): Fragment = fragments[p0]

    override fun getCount(): Int = list.size

    override fun getPageTitle(position: Int): CharSequence? {
        return Html.fromHtml(list[position].name)
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }
}