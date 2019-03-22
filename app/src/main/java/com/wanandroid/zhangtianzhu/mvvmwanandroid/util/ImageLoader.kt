package com.wanandroid.zhangtianzhu.mvvmwanandroid.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R


object ImageLoader {


    fun load(context: Context, imageView: ImageView, imageUrl: String?) {
        Glide.with(context).clear(imageView)
        val options = RequestOptions
                .diskCacheStrategyOf(DiskCacheStrategy.DATA)
                .placeholder(R.drawable.bg_placeholder)
        Glide.with(context)
                .load(imageUrl)
                .transition(DrawableTransitionOptions().crossFade())
                .apply(options)
                .into(imageView)

    }
}