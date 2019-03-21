package com.wanandroid.zhangtianzhu.mvvmwanandroid.util

import android.app.Activity
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.widget.TextView
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R

object SnackUtils {
    fun showSnackBar(activity: Activity, msg:String){
        val snackbar = Snackbar.make(activity.window.decorView,msg, Snackbar.LENGTH_SHORT)
        var view = snackbar.view
        (view.findViewById(R.id.snackbar_text) as TextView).setTextColor(ContextCompat.getColor(activity, R.color.white))
        snackbar.show()
    }
}