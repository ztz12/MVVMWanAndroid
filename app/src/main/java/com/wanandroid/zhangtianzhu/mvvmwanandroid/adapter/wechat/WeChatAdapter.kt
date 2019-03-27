package com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.wechat

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.text.Html
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.WeChatData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.knowledge.KnowledgeDetailFragment

class WeChatAdapter(val list: MutableList<WeChatData>, fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    private val fragmentList = mutableListOf<Fragment>()

    init {
        list.forEach {
            fragmentList.add(KnowledgeDetailFragment.getInstance(it.id))
        }
    }

    override fun getItem(p0: Int): Fragment = fragmentList[p0]

    override fun getCount(): Int = list.size

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return Html.fromHtml(list[position].name)
    }
}