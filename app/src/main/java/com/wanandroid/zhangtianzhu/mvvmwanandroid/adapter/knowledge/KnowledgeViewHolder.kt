package com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.knowledge

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseViewHolder
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.KnowledgeTreeData

class KnowledgeViewHolder(parent: ViewGroup) : BaseViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_knowledge_system, parent, false)
) {
    fun bindKnowledgeData(treeData: KnowledgeTreeData?) {
        treeData ?: return
        setTextView(R.id.first_title, treeData.name)
        val text = treeData.children.let {
            it.joinToString("  ", transform = { children ->
                Html.fromHtml(children.name)
            })
        }
        setTextView(R.id.second_title, text)
    }
}