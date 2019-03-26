package com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.collect

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.CollectionArticle
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.ImageLoader

class CollectViewHolder(parent: ViewGroup, private val context: Context) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_collect, parent, false)
) {
    private val collectAuthor = itemView.findViewById<TextView>(R.id.tv_collect_author)
    private val collectDate = itemView.findViewById<TextView>(R.id.tv_collect_date)
    private val image = itemView.findViewById<ImageView>(R.id.iv_collect_pic)
    private val collectArticle = itemView.findViewById<TextView>(R.id.tv_article)
    private val collectChapterName = itemView.findViewById<TextView>(R.id.tv_collect_chapterName)
    private val ivCollect = itemView.findViewById<ImageView>(R.id.iv_collect)

    fun bindCollectData(collectionArticle: CollectionArticle?, holder: CollectViewHolder) {
        collectionArticle ?:return
        collectAuthor.text = collectionArticle.author
        collectDate.text = collectionArticle.niceDate
        collectArticle.text = collectionArticle.title
        collectChapterName.text = collectionArticle.chapterName
        ivCollect.setImageResource(R.drawable.icon_like)
        if (collectionArticle.envelopePic.isNotEmpty()) {
            image.visibility = View.VISIBLE
            context.let {
                ImageLoader.load(it, image, collectionArticle.envelopePic)
            }
        } else {
            image.visibility = View.GONE
        }

        holder.ivCollect.setOnClickListener {
            if(onItemCollectListener!=null) {
                onItemCollectListener?.onCollectListener(collectionArticle)
            }
        }
    }

    private var onItemCollectListener: OnItemCollectListener? = null

    fun setOnCollectListener(onItemCollectListener: OnItemCollectListener) {
        this.onItemCollectListener = onItemCollectListener
    }

    interface OnItemCollectListener {
        fun onCollectListener(collectionArticle: CollectionArticle)
    }
}