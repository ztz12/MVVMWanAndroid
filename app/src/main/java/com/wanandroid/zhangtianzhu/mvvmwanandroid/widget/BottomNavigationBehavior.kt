package com.wanandroid.zhangtianzhu.mvvmwanandroid.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View

class BottomNavigationBehavior constructor(context: Context,attributeSet: AttributeSet):CoordinatorLayout.Behavior<View>(context,attributeSet) {
    private var outAnimator:ObjectAnimator?=null
    private var intAnimator:ObjectAnimator?=null

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes==ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        if(dy>0){
            if(outAnimator==null){
                outAnimator = ObjectAnimator.ofFloat(child,"translationY",0f,child.height.toFloat())
                outAnimator!!.duration =200
            }

            if(!outAnimator!!.isRunning&&child.translationY<=0){
                outAnimator!!.start()
            }
        }else if(dy<0){
            if(intAnimator==null){
                intAnimator = ObjectAnimator.ofFloat(child,"translationY",child.height.toFloat(),0f)
                intAnimator!!.duration = 200
            }

            if(!intAnimator!!.isRunning&&child.translationY>=child.height){
                intAnimator!!.start()
            }
        }
    }
}