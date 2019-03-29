package com.wanandroid.zhangtianzhu.mvvmwanandroid.base

import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView

open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private lateinit var mViews: SparseArray<View>

    init {
        mViews = SparseArray()
    }

    private fun <T : View> getView(@IdRes viewId: Int): T {
        var view = mViews[viewId]
        if (view == null) {
            view = itemView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T
    }

    fun setTextView(@IdRes id: Int, text: CharSequence): BaseViewHolder {
        val view = getView<TextView>(id)
        view.text = text
        return this
    }

    fun setImageResource(@IdRes id: Int, resourceId: Int): BaseViewHolder {
        val view = getView<ImageView>(id)
        view.setImageResource(resourceId)
        return this
    }

    fun setViewVisibility(@IdRes id: Int, visible: Boolean): BaseViewHolder {
        val view = getView<View>(id)
        view.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    fun setOnClickListener(@IdRes id: Int, listener: View.OnClickListener): BaseViewHolder {
        val view = getView<View>(id)
        view.setOnClickListener(listener)
        return this
    }

    fun setOnItemClickListener(listener: View.OnClickListener): BaseViewHolder {
        itemView.setOnClickListener(listener)
        return this
    }
}