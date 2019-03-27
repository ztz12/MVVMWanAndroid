package com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.knowledge

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.ArticleDetail
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.ImageLoader

class KnowledgeDetailViewHolder(parent:ViewGroup,private val context: Context):RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_knowledge_detail,parent,false)
){
    private val tvAuthor = itemView.findViewById<TextView>(R.id.tv_knowledge_author)
    private val tvDate = itemView.findViewById<TextView>(R.id.tv_knowledge_date)
    private val tvChapterName = itemView.findViewById<TextView>(R.id.tv_knowledge_chapterName)
    private val ivPic = itemView.findViewById<ImageView>(R.id.iv_knowledge_pic)
    private val tvArticle = itemView.findViewById<TextView>(R.id.tv_article)
    private val ivLike = itemView.findViewById<ImageView>(R.id.iv_knowledge)

    fun bindDetailData(item:ArticleDetail?,holder: KnowledgeDetailViewHolder){
        item ?: return
        tvArticle.text = item.title
        tvDate.text = item.niceDate
        tvAuthor.text = item.author
        tvChapterName.text = item.chapterName
        if (item.collect) {
            ivLike.setImageResource(R.drawable.icon_like)
        } else {
            ivLike.setImageResource(R.drawable.icon_like_article_not_selected)
        }
        if (item.envelopePic.isNotEmpty()) {
            ivPic.visibility = View.VISIBLE
            context.let {
                ImageLoader.load(it, ivPic, item.envelopePic)
            }
        } else {
            ivPic.visibility = View.GONE
        }
        holder.ivLike.setOnClickListener {
            if (onItemCollectListener != null) {
                onItemCollectListener?.onCollectListener(item)
            }
        }
    }

    private var onItemCollectListener: OnItemKnowledgeListener? = null

    fun setOnCollectListener(onItemCollectListener: OnItemKnowledgeListener) {
        this.onItemCollectListener = onItemCollectListener
    }

    interface OnItemKnowledgeListener {
        fun onCollectListener(articleDetail: ArticleDetail)
    }
}