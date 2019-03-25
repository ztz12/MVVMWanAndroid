package com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import cn.bingoogolapple.bgabanner.BGABanner
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.BannerData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.home.ContentActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.ImageLoader
import io.reactivex.Observable

class BannerViewHolder constructor(parent: ViewGroup, private val context: Context) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_bannerview, parent, false)
) {
    private val banner = itemView.findViewById<BGABanner>(R.id.banner)
    private lateinit var bannerData: List<BannerData>


    fun bindBannerData(bannerData: List<BannerData>) {
        this.bannerData = bannerData
        if (bannerData.isNotEmpty()) {
            val imagePic = ArrayList<String>()
            val imageTitle = ArrayList<String>()
            Observable.fromIterable(bannerData)
                    .subscribe {
                        imagePic.add(it.imagePath)
                        imageTitle.add(it.title)
                    }
            banner.run {
                setAutoPlayAble(imagePic.size > 1)
                setData(imagePic, imageTitle)
                setAdapter(bannerAdapter)
                setDelegate(delegate)
            }

        }
    }

    private val bannerAdapter: BGABanner.Adapter<ImageView, String> by lazy {
        BGABanner.Adapter<ImageView, String> { banner, ImageView, ImageUrl, position ->
            ImageLoader.load(context, ImageView, ImageUrl)
        }
    }

    private val delegate = BGABanner.Delegate<ImageView, String> { banner, itemView, model, position ->
        if (bannerData.isNotEmpty()) {
            ContentActivity.startContentBannerActivity(context, bannerData, position)
        }
    }
}