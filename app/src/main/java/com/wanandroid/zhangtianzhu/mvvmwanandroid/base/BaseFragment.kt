package com.wanandroid.zhangtianzhu.mvvmwanandroid.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.constant.Constants
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.DialogUtil
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.Preference
import me.yokeyword.fragmentation.SupportFragment

abstract class BaseFragment : SupportFragment() {

    protected var isLogin by Preference(Constants.ISLOGIN, false)

    private var themeCount = 0

    private lateinit var viewDataBinding: ViewDataBinding

    protected val mDialog by lazy { DialogUtil.getWaitDialog(_mActivity, getString(R.string.loading)) }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        init()
    }

    //通过DataBindingUtil.inflate加载布局，并返回加载当前binding的根布局,解决将Fragment添加到MainActivity中整体替换主页布局问题
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return viewDataBinding.root
    }

    private fun init() {
        getDataBinding()
        initData()
    }

    protected fun setRefreshThemeColor(refreshLayout: SmartRefreshLayout) {
        themeCount++
        when {
            themeCount % Constants.FOUR === Constants.ONE ->
                refreshLayout.setPrimaryColorsId(Constants.BLUE_THEME, R.color.white)
            themeCount % Constants.FOUR === Constants.TWO ->
                refreshLayout.setPrimaryColorsId(Constants.GREEN_THEME, R.color.white)
            themeCount % Constants.FOUR === Constants.THREE ->
                refreshLayout.setPrimaryColorsId(Constants.RED_THEME, R.color.white)
            themeCount % Constants.FOUR === Constants.ZERO ->
                refreshLayout.setPrimaryColorsId(Constants.ORANGE_THEME, R.color.white)
        }
    }

    abstract fun getLayoutId(): Int

    abstract fun initData()

    protected fun getDataBinding(): ViewDataBinding = viewDataBinding
}