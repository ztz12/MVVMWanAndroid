package com.wanandroid.zhangtianzhu.mvvmwanandroid.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View

class RecyclerItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private var mDivideDrawable: Drawable? = null
    private val intArray = intArrayOf(android.R.attr.listDivider)

    init {
        val a = context.obtainStyledAttributes(intArray)
        mDivideDrawable = a.getDrawable(0)
        a.recycle()
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (mDivideDrawable != null) {
            draw(c, parent)
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        if (mDivideDrawable != null) {
            draw(c, parent)
        }
    }

    //设置分割线偏移量
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(0,0,0,0)
    }

    private fun draw(c: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount

        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin + Math.round(ViewCompat.getTranslationY(child))
            val bottom = top + if (mDivideDrawable!!.intrinsicHeight <= 0) 1 else mDivideDrawable!!.intrinsicHeight
            mDivideDrawable?.let {
                it.setBounds(left, top, right, bottom)
                it.draw(c)
            }
        }

    }

    private fun getOrientation(layoutManager: RecyclerView.LayoutManager): Int {
        if (layoutManager is LinearLayoutManager) {
            return layoutManager.orientation
        } else if (layoutManager is StaggeredGridLayoutManager) {
            return layoutManager.orientation
        }

        return OrientationHelper.HORIZONTAL
    }
}