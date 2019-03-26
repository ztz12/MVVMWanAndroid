package com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.collect

import android.app.Activity
import android.arch.paging.PagedListAdapter
import android.content.Context
import android.content.Intent
import android.support.v7.util.DiffUtil
import android.view.View
import android.view.ViewGroup
import com.wanandroid.zhangtianzhu.mvvmwanandroid.constant.Constants
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.CollectionArticle
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.home.ContentActivity

class CollectAdapter constructor(private val context: Context, private val retryCallback: () -> Unit) : PagedListAdapter<CollectionArticle, CollectViewHolder>(itemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CollectViewHolder {
        return CollectViewHolder(parent, context)
    }

    override fun onBindViewHolder(holder: CollectViewHolder, position: Int) {
        holder.bindCollectData(getItem(position), holder)
        holder.itemView.tag = getItem(position)
        holder.itemView.setOnClickListener(onClickListener)
        holder.setOnCollectListener(object : CollectViewHolder.OnItemCollectListener {
            override fun onCollectListener(collectionArticle: CollectionArticle) {
                if (onCollectAdapterListener != null) {
                    onCollectAdapterListener?.collectListener(collectionArticle,position)
                }
            }
        })
    }

    companion object {

        private val itemCallback = object : DiffUtil.ItemCallback<CollectionArticle>() {
            override fun areItemsTheSame(p0: CollectionArticle, p1: CollectionArticle): Boolean = p0.id == p1.id

            override fun areContentsTheSame(p0: CollectionArticle, p1: CollectionArticle): Boolean = p0 == p1

        }
    }

    private val onClickListener = View.OnClickListener {
        retryCallback()
        val collectionArticle = it.tag as CollectionArticle
        val position = currentList?.indexOf(collectionArticle)
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

    private var onCollectAdapterListener: OnCollectAdapterListener? = null

    fun setOnCollectListener(onCollectAdapterListener: OnCollectAdapterListener) {
        this.onCollectAdapterListener = onCollectAdapterListener
    }

    interface OnCollectAdapterListener {
        fun collectListener(collectionArticle: CollectionArticle,position: Int)
    }
}