package com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.collect

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.CollectionArticle
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.ImageLoader

class MainCollectAdapter(private val context: Context, data:MutableList<CollectionArticle>) :
        BaseQuickAdapter<CollectionArticle,BaseViewHolder>(R.layout.item_collect,data) {
    override fun convert(helper: BaseViewHolder?, item: CollectionArticle?) {
        helper ?: return
        item ?: return

        helper.run {
            setText(R.id.tv_collect_author,item.author)
            setText(R.id.tv_collect_date,item.niceDate)
            setText(R.id.tv_collect_chapterName,item.chapterName)
            setText(R.id.tv_article,item.title)
            setImageResource(R.id.iv_collect,R.drawable.icon_like)
                    .addOnClickListener(R.id.iv_collect)
        }

        val image = helper?.getView<ImageView>(R.id.iv_collect_pic)
        if(item.envelopePic.isNotEmpty()){
            image.visibility = View.VISIBLE
            context.let {
                ImageLoader.load(it,image,item.envelopePic)
            }
        }else{
            image.visibility = View.GONE
        }
    }
}