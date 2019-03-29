package com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.knowledge

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseViewHolder
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.ArticleDetail
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.ImageLoader

class KnowledgeDetailViewHolder(parent: ViewGroup, private val context: Context) : BaseViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_knowledge_detail, parent, false)
) {
    private val ivPic = itemView.findViewById<ImageView>(R.id.iv_knowledge_pic)

    fun bindDetailData(item: ArticleDetail?) {
        item ?: return
        setTextView(R.id.tv_article, item.title)
        setTextView(R.id.tv_knowledge_date, item.niceDate)
        setTextView(R.id.tv_knowledge_author, item.author)
        setTextView(R.id.tv_knowledge_chapterName, item.chapterName)
        if (item.collect) {
            setImageResource(R.id.iv_knowledge, R.drawable.icon_like)
        } else {
            setImageResource(R.id.iv_knowledge, R.drawable.icon_like_article_not_selected)
        }
        if (item.envelopePic.isNotEmpty()) {
            setViewVisibility(R.id.iv_knowledge_pic, true)
            context.let {
                ImageLoader.load(it, ivPic, item.envelopePic)
            }
        } else {
            setViewVisibility(R.id.iv_knowledge_pic, false)
        }
        setOnClickListener(R.id.iv_knowledge, View.OnClickListener {
            if (onItemCollectListener != null) {
                onItemCollectListener?.onCollectListener(item)
            }
        })
    }

    private var onItemCollectListener: OnItemKnowledgeListener? = null

    fun setOnCollectListener(onItemCollectListener: OnItemKnowledgeListener) {
        this.onItemCollectListener = onItemCollectListener
    }

    interface OnItemKnowledgeListener {
        fun onCollectListener(articleDetail: ArticleDetail)
    }
}