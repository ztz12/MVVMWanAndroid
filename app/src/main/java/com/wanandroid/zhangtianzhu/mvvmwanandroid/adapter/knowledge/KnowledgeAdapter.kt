package com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.knowledge

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.util.DiffUtil
import android.view.View
import android.view.ViewGroup
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.KnowledgeTreeData

class KnowledgeAdapter(private val context: Context, private val retryCallback: () -> Unit) : PagedListAdapter<KnowledgeTreeData, KnowledgeViewHolder>(itemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): KnowledgeViewHolder {
        return KnowledgeViewHolder(parent, context)
    }

    override fun onBindViewHolder(holder: KnowledgeViewHolder, position: Int) {
        holder.bindKnowledgeData(getItem(position))
        holder.itemView.tag = getItem(position)
        holder.itemView.setOnClickListener(onClickListener)
    }

    companion object {
        private val itemCallback = object : DiffUtil.ItemCallback<KnowledgeTreeData>() {
            override fun areItemsTheSame(p0: KnowledgeTreeData, p1: KnowledgeTreeData): Boolean = p0.id == p1.id

            override fun areContentsTheSame(p0: KnowledgeTreeData, p1: KnowledgeTreeData): Boolean = p0 == p1

        }
    }

    private val onClickListener = View.OnClickListener {
        retryCallback()
    }
}