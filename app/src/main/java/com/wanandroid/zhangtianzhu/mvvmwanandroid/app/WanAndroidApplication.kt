package com.wanandroid.zhangtianzhu.mvvmwanandroid.app

import android.content.Context
import android.support.multidex.MultiDex
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.DisplayManager
import org.litepal.LitePal
import org.litepal.LitePalApplication
import kotlin.properties.Delegates

class WanAndroidApplication : LitePalApplication() {

    companion object {
        var context: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        DisplayManager.init(this)
        initLitePal()
    }

    private fun initLitePal() {
        LitePal.initialize(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}