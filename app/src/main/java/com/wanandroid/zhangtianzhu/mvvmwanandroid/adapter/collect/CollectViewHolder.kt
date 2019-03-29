package com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.collect

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseViewHolder
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.CollectionArticle
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.ImageLoader

class CollectViewHolder(parent: ViewGroup, private val context: Context) : BaseViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_collect, parent, false)
) {
    private val image = itemView.findViewById<ImageView>(R.id.iv_collect_pic)

    fun bindCollectData(collectionArticle: CollectionArticle?) {
        collectionArticle ?: return
        setTextView(R.id.tv_collect_author, collectionArticle.author)
        setTextView(R.id.tv_collect_date, collectionArticle.niceDate)
        setTextView(R.id.tv_article, collectionArticle.title)
        setTextView(R.id.tv_collect_chapterName, collectionArticle.chapterName)
        setImageResource(R.id.iv_collect, R.drawable.icon_like)
        if (collectionArticle.envelopePic.isNotEmpty()) {
            setViewVisibility(R.id.iv_collect_pic, true)
            context.let {
                ImageLoader.load(it, image, collectionArticle.envelopePic)
            }
        } else {
            setViewVisibility(R.id.iv_collect_pic, false)
        }

        setOnClickListener(R.id.iv_collect, View.OnClickListener {
            if (onItemCollectListener != null) {
                onItemCollectListener?.onCollectListener(collectionArticle)
            }
        })
    }

    private var onItemCollectListener: OnItemCollectListener? = null

    fun setOnCollectListener(onItemCollectListener: OnItemCollectListener) {
        this.onItemCollectListener = onItemCollectListener
    }

    interface OnItemCollectListener {
        fun onCollectListener(collectionArticle: CollectionArticle)
    }
}