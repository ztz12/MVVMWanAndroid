package com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.home

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.util.DiffUtil
import android.view.View
import android.view.ViewGroup
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.ArticleDetail
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.home.ContentActivity

class HomeAdapter constructor(private val context: Context,private val retryCallback:()->Unit) : PagedListAdapter<ArticleDetail, HomeViewHolder>(itemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): HomeViewHolder = HomeViewHolder(parent, context)

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bindArticle(getItem(position))
        holder.itemView.tag = getItem(position)
        holder.itemView.setOnClickListener(onClickListener)
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
        if(position!=null&&list!=null){
            ContentActivity.startContentActivity(it.context,list,position)
        }
    }
}