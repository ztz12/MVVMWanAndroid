package com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.knowledge

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.KnowledgeTreeData

class KnowledgeViewHolder (parent:ViewGroup,private val context: Context):RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_knowledge_system,parent,false)
){
    private val firstTitle = itemView.findViewById<TextView>(R.id.first_title)
    private val secondTitle = itemView.findViewById<TextView>(R.id.second_title)

    fun bindKnowledgeData(treeData: KnowledgeTreeData?){
        treeData ?: return
        firstTitle.text = treeData.name
        secondTitle.text = treeData.children.let {
            it.joinToString ("  ",transform = { children->
                Html.fromHtml(children.name)
            })
        }
    }
}