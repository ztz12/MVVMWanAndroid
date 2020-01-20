package com.wanandroid.zhangtianzhu.mvvmwanandroid.app

import android.content.Context
import android.support.multidex.MultiDex
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.DisplayManager
import org.litepal.LitePal
import org.litepal.LitePalApplication
import java.util.concurrent.Executors
import kotlin.properties.Delegates

class WanAndroidApplication : LitePalApplication() {

    private val CPU_COUNT = Runtime.getRuntime().availableProcessors()
    private val CORE_POOL_SIZE = 2.coerceAtLeast((CPU_COUNT - 1).coerceAtMost(4))

    companion object {
        var context: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        DisplayManager.init(this)
        val service = Executors.newFixedThreadPool(CORE_POOL_SIZE)
        service.submit {
            initLitePal()
        }
        service.shutdown()
    }

    private fun initLitePal() {
        LitePal.initialize(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}