package com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.navigation

import android.content.Context
import android.support.v4.content.ContextCompat
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.NavigationData
import q.rorbin.verticaltablayout.adapter.TabAdapter
import q.rorbin.verticaltablayout.widget.ITabView

class NavigationTabAdapter(private val context: Context, private val navigationDataList: MutableList<NavigationData>) : TabAdapter {
    override fun getIcon(position: Int): ITabView.TabIcon? = null

    override fun getBadge(position: Int): ITabView.TabBadge? = null

    override fun getBackground(position: Int): Int = -1

    override fun getTitle(position: Int): ITabView.TabTitle {
        return ITabView.TabTitle.Builder()
                .setContent(navigationDataList[position].name)
                .setTextColor(ContextCompat.getColor(context, R.color.colorPrimary), ContextCompat.getColor(context, R.color.Grey400))
                .build()
    }

    override fun getCount(): Int = navigationDataList.size

}