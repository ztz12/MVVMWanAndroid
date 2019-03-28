package com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.navigation

import android.app.ActivityOptions
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.constant.Constants
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.ArticleDetail
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.NavigationData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.home.ContentActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.CommonUtil
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.DisplayManager
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout

class NavigationAdapter(data: MutableList<NavigationData>) :
        BaseQuickAdapter<NavigationData, BaseViewHolder>(R.layout.item_navigation, data) {
    override fun convert(helper: BaseViewHolder?, item: NavigationData?) {
        helper ?: return
        item ?: return
        helper.setText(R.id.item_navigation_tv, item.name)
        val flowLayout: TagFlowLayout = helper.getView(R.id.item_navigation_flow_layout)
        val articles: MutableList<ArticleDetail> = item.articles
        flowLayout.run {
            adapter = object : TagAdapter<ArticleDetail>(articles) {
                override fun getView(parent: FlowLayout?, position: Int, t: ArticleDetail?): View {
                    val tv: TextView = LayoutInflater.from(parent?.context).inflate(R.layout.flow_layout_tv, parent, false) as TextView

                    val padding = DisplayManager.dip2px(10f)!!
                    tv.setPadding(padding, padding, padding, padding)
                    tv.setTextColor(CommonUtil.randomColor())
                    tv.text = articles[position].title
                    setOnTagClickListener { view, position, parent ->
                        val options = ActivityOptions.makeScaleUpAnimation(view,
                                view.width / 2,
                                view.height / 2,
                                0, 0)
                        val data = articles[position]
                        Intent(context, ContentActivity::class.java).run {
                            putExtra(Constants.CONTENT_URL_KEY, data.link)
                            putExtra(Constants.CONTENT_TITLE_KEY, data.title)
                            putExtra(Constants.CONTENT_ID_KEY, data.id)
                            context.startActivity(this, options.toBundle())
                        }
                        true
                    }
                    return tv
                }

            }
        }
    }
}