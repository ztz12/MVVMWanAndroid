package com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.knowledge

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.content.Intent
import android.support.v7.util.DiffUtil
import android.view.View
import android.view.ViewGroup
import com.wanandroid.zhangtianzhu.mvvmwanandroid.constant.Constants
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.ArticleDetail
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.home.ContentActivity

class KnowledgeDetailAdapter (val context: Context, private val retryCallback: () -> Unit) : PagedListAdapter<ArticleDetail,KnowledgeDetailViewHolder >(itemCallback)  {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): KnowledgeDetailViewHolder = KnowledgeDetailViewHolder(p0,context)

    override fun onBindViewHolder(holder: KnowledgeDetailViewHolder, position: Int) {
        holder.bindDetailData(getItem(position))
        holder.itemView.tag = getItem(position)
        holder.itemView.setOnClickListener(onClickListener)
        holder.setOnCollectListener(object :KnowledgeDetailViewHolder.OnItemKnowledgeListener{
            override fun onCollectListener(articleDetail: ArticleDetail) {
                if(onKnowledgeCollectListener!=null){
                    onKnowledgeCollectListener?.knowledgeCollectListener(articleDetail)
                }
            }
        })
    }

    companion object {
        private val itemCallback = object : DiffUtil.ItemCallback<ArticleDetail>() {
            override fun areItemsTheSame(p0: ArticleDetail, p1: ArticleDetail): Boolean = p0.id == p1.id

            override fun areContentsTheSame(p0: ArticleDetail, p1: ArticleDetail): Boolean = p0 == p1

        }
    }

    private val onClickListener = View.OnClickListener {
        retryCallback()
        val articleDetail = it.tag as ArticleDetail
        val position = currentList?.indexOf(articleDetail)
        val list = currentList?.toList()
        if (position != null && list != null) {
            Intent(context, ContentActivity::class.java).run {
                putExtra(Constants.CONTENT_ID_KEY, list[position].id)
                putExtra(Constants.CONTENT_URL_KEY, list[position].link)
                putExtra(Constants.CONTENT_TITLE_KEY, list[position].title)
                context.startActivity(this)
            }
        }
    }

    private var onKnowledgeCollectListener: OnKnowledgeCollectListener? = null

    fun setOnKnowledgeCollectListener(onKnowledgeCollectListener: OnKnowledgeCollectListener) {
        this.onKnowledgeCollectListener = onKnowledgeCollectListener
    }

    interface OnKnowledgeCollectListener {
        fun knowledgeCollectListener(articleDetail: ArticleDetail)
    }

}