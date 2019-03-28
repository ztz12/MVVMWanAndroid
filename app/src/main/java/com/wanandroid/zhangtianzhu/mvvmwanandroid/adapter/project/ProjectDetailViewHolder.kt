package com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.project

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.ArticleDetail
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.ImageLoader

class ProjectDetailViewHolder(parent: ViewGroup, private val context: Context) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_project_list, parent, false)
) {
    private val ivProject = itemView.findViewById<ImageView>(R.id.item_project_list_iv)
    private val tvTitle = itemView.findViewById<TextView>(R.id.item_project_list_title_tv)
    private val tvContent = itemView.findViewById<TextView>(R.id.item_project_list_content_tv)
    private val tvTime = itemView.findViewById<TextView>(R.id.item_project_list_time_tv)
    private val tvAuthor = itemView.findViewById<TextView>(R.id.item_project_list_author_tv)
    private val ivLike = itemView.findViewById<ImageView>(R.id.item_project_list_like_iv)

    fun bindDetailData(item: ArticleDetail?, holder: ProjectDetailViewHolder) {
        item ?: return
        tvTitle.text = Html.fromHtml(item.title)
        tvTime.text = item.niceDate
        tvAuthor.text = item.author
        tvContent.text = Html.fromHtml(item.desc)
        if (item.collect) {
            ivLike.setImageResource(R.drawable.icon_like)
        } else {
            ivLike.setImageResource(R.drawable.icon_like_article_not_selected)
        }
        context.let {
            ImageLoader.load(it, ivProject, item.envelopePic)
        }
        holder.ivLike.setOnClickListener {
            if (onItemCollectListener != null) {
                onItemCollectListener?.onCollectListener(item)
            }
        }
    }

    private var onItemCollectListener: OnItemProjectListener? = null

    fun setOnCollectListener(onItemCollectListener: OnItemProjectListener) {
        this.onItemCollectListener = onItemCollectListener
    }

    interface OnItemProjectListener {
        fun onCollectListener(articleDetail: ArticleDetail)
    }
}