package com.wanandroid.zhangtianzhu.mvvmwanandroid.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import com.wanandroid.zhangtianzhu.mvvmwanandroid.bean.event.LoginEvent
import com.wanandroid.zhangtianzhu.mvvmwanandroid.constant.Constants
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.Preference
import me.yokeyword.fragmentation.SupportActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

abstract class BaseActivity : SupportActivity() {
    private lateinit var viewDataBinding: ViewDataBinding

    protected var user: String by Preference(Constants.USERNAME, "")

    protected var isLogin by Preference(Constants.ISLOGIN, false)

    protected var isFirstIn by Preference(Constants.ISFIRSTIN, true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        EventBus.getDefault().register(this)
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        getDataBinding()
        initData()
        onViewSaveInstance(savedInstanceState)
    }

    abstract fun initData()

    abstract fun getLayoutId(): Int

    abstract fun onViewSaveInstance(savedInstanceState: Bundle?)

    protected fun getDataBinding(): ViewDataBinding = viewDataBinding

    @Subscribe
    fun onEvent(loginEvent: LoginEvent){

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}