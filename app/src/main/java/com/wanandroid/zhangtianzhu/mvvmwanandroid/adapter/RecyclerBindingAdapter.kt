package com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.wanandroid.zhangtianzhu.mvvmwanandroid.bean.User
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.databinding.ItemBindingBinding

class RecyclerBindingAdapter constructor(private var list: MutableList<User>):RecyclerView.Adapter<RecyclerBindingAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val itemBindingBinding:ItemBindingBinding = DataBindingUtil.inflate(LayoutInflater.from(p0.context),R.layout.item_binding,p0,false)
        return ViewHolder(itemBindingBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val user:User = list[p1]
        p0.itemBindingBinding!!.user = user
    }

    class ViewHolder(binding: ViewDataBinding):RecyclerView.ViewHolder(binding.root){
        var itemBindingBinding:ItemBindingBinding?=null

        init {
            itemBindingBinding = binding as ItemBindingBinding
        }
    }

}