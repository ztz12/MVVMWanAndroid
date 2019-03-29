package com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.project

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseViewHolder
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.ArticleDetail
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.ImageLoader

class ProjectDetailViewHolder(parent: ViewGroup, private val context: Context) : BaseViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_project_list, parent, false)
) {
    private val ivProject = itemView.findViewById<ImageView>(R.id.item_project_list_iv)

    fun bindDetailData(item: ArticleDetail?) {
        item ?: return
        setTextView(R.id.item_project_list_title_tv, Html.fromHtml(item.title))
        setTextView(R.id.item_project_list_time_tv, item.niceDate)
        setTextView(R.id.item_project_list_author_tv, item.author)
        setTextView(R.id.item_project_list_content_tv, Html.fromHtml(item.desc))
        if (item.collect) {
            setImageResource(R.id.item_project_list_like_iv, R.drawable.icon_like)
        } else {
            setImageResource(R.id.item_project_list_like_iv, R.drawable.icon_like_article_not_selected)
        }
        context.let {
            ImageLoader.load(it, ivProject, item.envelopePic)
        }
        setOnClickListener(R.id.item_project_list_like_iv, View.OnClickListener {
            if (onItemCollectListener != null) {
                onItemCollectListener?.onCollectListener(item)
            }
        })
    }

    private var onItemCollectListener: OnItemProjectListener? = null

    fun setOnCollectListener(onItemCollectListener: OnItemProjectListener) {
        this.onItemCollectListener = onItemCollectListener
    }

    interface OnItemProjectListener {
        fun onCollectListener(articleDetail: ArticleDetail)
    }
}