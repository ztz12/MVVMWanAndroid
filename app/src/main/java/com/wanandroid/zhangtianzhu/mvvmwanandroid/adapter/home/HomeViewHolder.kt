package com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.ArticleDetail
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.ImageLoader

class HomeViewHolder(parent: ViewGroup,private val context: Context) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_home_layout, parent, false)
) {
    private val homeTop = itemView.findViewById<TextView>(R.id.tv_home_top)
    private val homeNewText = itemView.findViewById<TextView>(R.id.tv_home_refresh)
    private val articleTag = itemView.findViewById<TextView>(R.id.tv_home_article_tag)
    private val author = itemView.findViewById<TextView>(R.id.tv_home_author)
    private val homeDate = itemView.findViewById<TextView>(R.id.tv_home_date)
    private val homePic = itemView.findViewById<ImageView>(R.id.iv_home_pic)
    private val homeArticle = itemView.findViewById<TextView>(R.id.tv_article)
    private val homeChapterName = itemView.findViewById<TextView>(R.id.tv_home_chapterName)
    private val collect = itemView.findViewById<ImageView>(R.id.iv_collect)

    fun bindArticle(articleDetail: ArticleDetail?){
        articleDetail?:return
        homeArticle.text = Html.fromHtml(articleDetail.title)
        author.text = articleDetail.author
        homeDate.text = articleDetail.niceDate
        if(articleDetail.collect){
            collect.setImageResource(R.drawable.icon_like)
        }else{
            collect.setImageResource(R.drawable.icon_like_article_not_selected)
        }
        if(articleDetail.envelopePic.isNotEmpty()){
            homePic.visibility = View.VISIBLE
            context.let {
                ImageLoader.load(it,homePic,articleDetail.envelopePic)
            }
        }else{
            homePic.visibility = View.GONE
        }

        if(articleDetail.top =="1"){
            homeTop.visibility = View.VISIBLE
        }else{
            homeTop.visibility = View.GONE
        }

        //显示"新"
        if(articleDetail.fresh){
            homeNewText.visibility = View.VISIBLE
        }else{
            homeNewText.visibility = View.GONE
        }

        //标签
        if(articleDetail.tags.size>0){
            articleTag.visibility = View.VISIBLE
            articleTag.text = articleDetail.tags[0].name
        }else{
            articleTag.visibility = View.GONE
        }

        //项目分级
        val chapterName = when{
            articleDetail.superChapterName.isNotEmpty() and articleDetail.chapterName.isNotEmpty() ->
                "${articleDetail.superChapterName}/${articleDetail.chapterName}"
            articleDetail.superChapterName.isNotEmpty() -> "${articleDetail.superChapterName}"
            articleDetail.chapterName.isNotEmpty() -> "{${articleDetail.chapterName}}"
            else -> ""
        }
        homeChapterName.text = chapterName

    }


}