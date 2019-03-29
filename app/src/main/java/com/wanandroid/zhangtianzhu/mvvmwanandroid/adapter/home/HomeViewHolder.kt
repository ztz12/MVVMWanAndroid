package com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.home

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

class HomeViewHolder(parent: ViewGroup, private val context: Context) : BaseViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_home_layout, parent, false)
) {
    private val homePic = itemView.findViewById<ImageView>(R.id.iv_home_pic)

    fun bindArticle(articleDetail: ArticleDetail?) {
        articleDetail ?: return
        setTextView(R.id.tv_article, Html.fromHtml(articleDetail.title))
        setTextView(R.id.tv_home_author, articleDetail.author)
        setTextView(R.id.tv_home_date, articleDetail.niceDate)
        if (articleDetail.collect) {
            setImageResource(R.id.iv_collect, R.drawable.icon_like)
        } else {
            setImageResource(R.id.iv_collect, R.drawable.icon_like_article_not_selected)
        }
        if (articleDetail.envelopePic.isNotEmpty()) {
            setViewVisibility(R.id.iv_home_pic, true)
            context.let {
                ImageLoader.load(it, homePic, articleDetail.envelopePic)
            }
        } else {
            setViewVisibility(R.id.iv_home_pic, false)
        }

        if (articleDetail.top == "1") {
            setViewVisibility(R.id.tv_home_top, true)
        } else {
            setViewVisibility(R.id.tv_home_top, false)
        }

        //显示"新"
        if (articleDetail.fresh) {
            setViewVisibility(R.id.tv_home_refresh, true)
        } else {
            setViewVisibility(R.id.tv_home_refresh, false)
        }

        //标签
        if (articleDetail.tags.size > 0) {
            setViewVisibility(R.id.tv_home_article_tag, true)
            setTextView(R.id.tv_home_article_tag, articleDetail.tags[0].name)
        } else {
            setViewVisibility(R.id.tv_home_article_tag, false)
        }

        //项目分级
        val chapterName = when {
            articleDetail.superChapterName.isNotEmpty() and articleDetail.chapterName.isNotEmpty() ->
                "${articleDetail.superChapterName}/${articleDetail.chapterName}"
            articleDetail.superChapterName.isNotEmpty() -> "${articleDetail.superChapterName}"
            articleDetail.chapterName.isNotEmpty() -> "{${articleDetail.chapterName}}"
            else -> ""
        }
        setTextView(R.id.tv_home_chapterName, chapterName)

        setOnClickListener(R.id.iv_collect, View.OnClickListener {
            if (onItemCollectListener != null) {
                onItemCollectListener?.onCollectListener(articleDetail)
            }
        })

    }

    private var onItemCollectListener: OnItemCollectListener? = null

    fun setOnCollectListener(onItemCollectListener: OnItemCollectListener) {
        this.onItemCollectListener = onItemCollectListener
    }

    interface OnItemCollectListener {
        fun onCollectListener(articleDetail: ArticleDetail)
    }


}