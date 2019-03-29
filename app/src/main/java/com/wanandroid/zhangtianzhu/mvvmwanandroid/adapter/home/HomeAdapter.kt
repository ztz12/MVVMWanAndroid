package com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.home

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.content.Intent
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.wanandroid.zhangtianzhu.mvvmwanandroid.constant.Constants
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.ArticleDetail
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.BannerData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.home.ContentActivity

class HomeAdapter constructor(private val context: Context, private val bannerData: List<BannerData>, private val retryCallback: () -> Unit) : PagedListAdapter<ArticleDetail, RecyclerView.ViewHolder>(itemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HOME_DATA -> {
                HomeViewHolder(parent, context)
            }
            else -> {
                BannerViewHolder(parent, context)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeViewHolder -> {
                holder.bindArticle(getItem(position - 1))
                holder.itemView.tag = getItem(position - 1)
                holder.itemView.setOnClickListener(onClickListener)
                holder.setOnCollectListener(object : HomeViewHolder.OnItemCollectListener {
                    override fun onCollectListener(articleDetail: ArticleDetail) {
                        if (onHomeCollectListener != null) {
                            onHomeCollectListener?.homeCollectListener(articleDetail)
                        }
                    }

                })
            }
            is BannerViewHolder -> {
                holder.bindBannerData(bannerData)
            }

        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> HOME_HEAD
            else -> HOME_DATA
        }
    }


    companion object {
        const val HOME_HEAD = 0
        const val HOME_DATA = 1
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

    private var onHomeCollectListener: OnHomeCollectListener? = null

    fun setOnHomeCollectListener(onHomeCollectListener: OnHomeCollectListener) {
        this.onHomeCollectListener = onHomeCollectListener
    }

    interface OnHomeCollectListener {
        fun homeCollectListener(articleDetail: ArticleDetail)
    }
}