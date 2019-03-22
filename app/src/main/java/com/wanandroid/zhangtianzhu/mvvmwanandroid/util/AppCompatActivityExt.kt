package com.wanandroid.zhangtianzhu.mvvmwanandroid.util

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity

fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment,@IdRes id:Int,tag:String) {
    supportFragmentManager.transct {
        replace(id,fragment,tag)
    }
}

private inline fun FragmentManager.transct(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        setCustomAnimations(
                com.wanandroid.zhangtianzhu.mvvmwanandroid.R.anim.grow_fade_in_from_bottom,
                com.wanandroid.zhangtianzhu.mvvmwanandroid.R.anim.fade_out,
                com.wanandroid.zhangtianzhu.mvvmwanandroid.R.anim.fade_in,
                com.wanandroid.zhangtianzhu.mvvmwanandroid.R.anim.shrink_fade_out_from_bottom
        )
        action()
    }.commitAllowingStateLoss()
}

fun <T : ViewModel> AppCompatActivity.initViewModel(viewModelClass: Class<T>) =
        ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(viewModelClass)