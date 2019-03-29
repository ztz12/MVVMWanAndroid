package com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity

import android.arch.lifecycle.Observer
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.View
import android.widget.TextView
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.bean.event.LoginEvent
import com.wanandroid.zhangtianzhu.mvvmwanandroid.constant.Constants
import com.wanandroid.zhangtianzhu.mvvmwanandroid.databinding.ActivityMainBinding
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.login.LoginActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.home.HomeFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.home.MainCollectFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.knowledge.KnowledgeFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.navigation.NavigationFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.project.ProjectFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.wechat.WeChatFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.*
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.collect.CollectViewModel
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.home.HomeViewModel
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.home.MainViewModel
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.knowledge.KnowledgeDetailViewModel
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.knowledge.KnowledgeViewModel
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.navigation.NavigationViewModel
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.project.ProjectDetailViewModel
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.project.ProjectViewModel
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.wechat.WeChatViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.common_toolbar.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread

class MainActivity : BaseActivity() {

    private var mType: String? = null

    private var homeFragment: HomeFragment? = null
    private var knowledgeFragment: KnowledgeFragment? = null
    private var weChatFragment: WeChatFragment? = null
    private var navigationFragment: NavigationFragment? = null
    private var projectFragment: ProjectFragment? = null
    private var mainCollectFragment: MainCollectFragment? = null

    private var tvUser: TextView? = null

    private var binding: ActivityMainBinding? = null

    private lateinit var mViewModel: MainViewModel

    private val mDialog by lazy { DialogUtil.getWaitDialog(this, getString(R.string.loginOut_ing)) }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onViewSaveInstance(savedInstanceState: Bundle?) {
    }

    override fun initData() {
        binding = getDataBinding() as ActivityMainBinding
        mViewModel = obtainMainModel()
        mType = Constants.TYPE_HOME
        setSupportActionBar(common_toolbar)
        val actionBar = supportActionBar!!
        actionBar.setDisplayShowTitleEnabled(false)
        StatusBarUtil.setStatusColor(window, ContextCompat.getColor(this, R.color.main_status_bar_blue), 1.0f)
        common_toolbar.setNavigationOnClickListener { onBackPressedSupport() }
        navigation.run {
            tvUser = getHeaderView(0).findViewById(R.id.nav_header_login_tv)
        }
        initBottomNavigation()
        initDrawLayout()
        initNavigationItem()
        HomeFragment.newInstance().also {
            homeFragment = it
            replaceFragmentInActivity(it, R.id.fl_page, Constants.TYPE_HOME)
            common_toolbar_title_tv.text = Constants.TYPE_HOME
        }
        initFab()
    }

    private fun initBottomNavigation() {
        bottom_navigation_view.run {
            labelVisibilityMode = 1
            setOnNavigationItemSelectedListener(onNavigationItemReselectedListener)
        }
    }

    private fun initDrawLayout() {
        val toggle = object : ActionBarDrawerToggle(
                this, drawer_layout, common_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            //侧栏滑动时
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                //获取mDrawerLayout中的第一个子布局，也就是布局中的RelativeLayout
                //获取抽屉的view
                val mContent = drawer_layout.getChildAt(0)
                val scale = 1 - slideOffset
                val endScale = 0.8f + scale * 0.2f
                val startScale = 1 - 0.3f * scale

                //设置左边菜单滑动后的占据屏幕大小
                drawerView.scaleX = startScale
                drawerView.scaleY = startScale
                //设置菜单透明度
                drawerView.alpha = 0.6f + 0.4f * (1 - scale)

                //设置内容界面水平和垂直方向偏转量
                //在滑动时内容界面的宽度为 屏幕宽度减去菜单界面所占宽度
                mContent.translationX = drawerView.measuredWidth * (1 - scale)
                //设置内容界面操作无效（比如有button就会点击无效）
                mContent.invalidate()
                //设置右边菜单滑动后的占据屏幕大小
                mContent.scaleX = endScale
                mContent.scaleY = endScale
            }
        }
        toggle.syncState()
        drawer_layout.addDrawerListener(toggle)
    }

    private fun initFab() {
        fab.setOnClickListener {
            when (mType) {
                Constants.TYPE_HOME -> homeFragment?.scrollTop()
                Constants.TYPE_KNOWLEDGE -> knowledgeFragment?.scrollTop()
                Constants.TYPE_WECHAT -> weChatFragment?.scrollTop()
                Constants.TYPE_NAVIGATION -> navigationFragment?.scrollTop()
                Constants.TYPE_PROJECT -> projectFragment?.scrollTop()
                Constants.TYPE_COLLECT -> mainCollectFragment?.scrollTop()
            }

        }
    }

    override fun onBackPressedSupport() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressedSupport()
        }
    }

    private val onNavigationItemReselectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                return@OnNavigationItemSelectedListener when (item.itemId) {
                    R.id.tab_home_pager -> {
                        mType = Constants.TYPE_HOME
                        if (null == supportFragmentManager.findFragmentByTag(Constants.TYPE_HOME)) {
                            //重新创建新的实例添加，否则recyclerView会报LinearLayoutManager被多次添加异常
                            HomeFragment.newInstance().also {
                                //重新赋值实例，获取最新的实例对象
                                homeFragment = it
                                replaceFragmentInActivity(it, R.id.fl_page, Constants.TYPE_HOME)
                                common_toolbar_title_tv.text = Constants.TYPE_HOME
                            }
                        }
                        true
                    }
                    R.id.tab_knowledge_hierarchy -> {
                        mType = Constants.TYPE_KNOWLEDGE
                        if (null == supportFragmentManager.findFragmentByTag(Constants.TYPE_KNOWLEDGE)) {
                            KnowledgeFragment.newInstance().also {
                                knowledgeFragment = it
                                replaceFragmentInActivity(it, R.id.fl_page, Constants.TYPE_KNOWLEDGE)
                                common_toolbar_title_tv.text = Constants.TYPE_KNOWLEDGE
                            }
                        }
                        true
                    }
                    R.id.tab_weChat -> {
                        mType = Constants.TYPE_WECHAT
                        if (null == supportFragmentManager.findFragmentByTag(Constants.TYPE_WECHAT)) {
                            WeChatFragment.newInstance().also {
                                weChatFragment = it
                                replaceFragmentInActivity(it, R.id.fl_page, Constants.TYPE_WECHAT)
                                common_toolbar_title_tv.text = Constants.TYPE_WECHAT
                            }
                        }
                        true
                    }
                    R.id.tab_navigation -> {
                        mType = Constants.TYPE_NAVIGATION
                        if (null == supportFragmentManager.findFragmentByTag(Constants.TYPE_NAVIGATION)) {
                            NavigationFragment.newInstance().also {
                                navigationFragment = it
                                replaceFragmentInActivity(it, R.id.fl_page, Constants.TYPE_NAVIGATION)
                                common_toolbar_title_tv.text = Constants.TYPE_NAVIGATION
                            }
                        }
                        true
                    }
                    else -> {
                        mType = Constants.TYPE_PROJECT
                        if (null == supportFragmentManager.findFragmentByTag(Constants.TYPE_PROJECT)) {
                            ProjectFragment.newInstance().also {
                                projectFragment = it
                                replaceFragmentInActivity(it, R.id.fl_page, Constants.TYPE_PROJECT)
                                common_toolbar_title_tv.text = Constants.TYPE_PROJECT
                            }
                        }
                        true
                    }
                }

            }

    private fun initNavigationItem() {
        tvUser?.run {
            text = if (!isLogin) {
                getString(R.string.login_in)
            } else {
                user
            }
            setOnClickListener {
                if (!isLogin) {
                    startActivity<LoginActivity>()
                }
            }
        }
        navigation.menu.findItem(R.id.nav_item_my_collect).setOnMenuItemClickListener {
            mType = Constants.TYPE_COLLECT
            if (isLogin) {
                if (null == supportFragmentManager.findFragmentByTag(Constants.TYPE_COLLECT)) {
                    MainCollectFragment.newInstance().also {
                        mainCollectFragment = it
                        replaceFragmentInActivity(it, R.id.fl_page, Constants.TYPE_COLLECT)
                        common_toolbar_title_tv.text = Constants.TYPE_COLLECT
                    }
                }
                bottom_navigation_view.visibility = View.INVISIBLE
                common_toolbar_title_tv.text = Constants.TYPE_COLLECT
                closeDrawer()
            } else {
                DialogUtil.showSnackBar(this, getString(R.string.login_tint))
            }
            true
        }

        navigation.menu.findItem(R.id.nav_item_setting).setOnMenuItemClickListener {
            closeDrawer()
            true
        }

        navigation.menu.findItem(R.id.nav_item_home).setOnMenuItemClickListener {
            mType = Constants.TYPE_HOME
            if (null == supportFragmentManager.findFragmentByTag(Constants.TYPE_HOME)) {
                HomeFragment.newInstance().also {
                    homeFragment = it
                    replaceFragmentInActivity(it, R.id.fl_page, Constants.TYPE_HOME)
                }
            }
            bottom_navigation_view.selectedItemId = R.id.tab_home_pager
            fab.visibility = View.VISIBLE
            bottom_navigation_view.visibility = View.VISIBLE
            common_toolbar_title_tv.text = Constants.TYPE_HOME
            closeDrawer()
            true
        }

        navigation.menu.findItem(R.id.nav_item_about_us).setOnMenuItemClickListener {
            closeDrawer()
            true
        }

        navigation.menu.findItem(R.id.nav_item_todo).setOnMenuItemClickListener {
            closeDrawer()
            true
        }

        //退出登录
        navigation.menu.findItem(R.id.nav_item_logout).run {
            isVisible = isLogin
            setOnMenuItemClickListener {
                DialogUtil.getConfirmDialog(this@MainActivity, getString(R.string.logout_tint)
                        , DialogInterface.OnClickListener { _, _ ->
                    mDialog.show()
                    mViewModel.loginOut()
                    mViewModel.loginOutSuccess.observe(this@MainActivity, Observer {
                        if (it!!) {
                            doAsync {
                                Preference.clearPreference()
                                uiThread {
                                    mDialog.dismiss()
                                    isLogin = false
                                    startActivity<LoginActivity>()
                                    EventBus.getDefault().post(LoginEvent(false))
                                }
                            }
                        }
                    })
                    closeDrawer()
                }).show()
                true
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onisLogin(loginEvent: LoginEvent) {
        if (loginEvent.isLogin) {
            isLogin = true
            tvUser?.text = user
            navigation.menu.findItem(R.id.nav_item_logout).isVisible = true
            drawer_layout.closeDrawers()
        } else {
            tvUser?.text = getString(R.string.login_in)
            navigation.menu.findItem(R.id.nav_item_logout).isVisible = false
        }
    }

    private fun closeDrawer() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
    }

    private fun obtainMainModel(): MainViewModel = initViewModel(MainViewModel::class.java)
    fun obtainHomeModel(): HomeViewModel = initViewModel(HomeViewModel::class.java)
    fun obtainCollectModel(): CollectViewModel = initViewModel(CollectViewModel::class.java)
    fun obtainKnowledgeModel(): KnowledgeViewModel = initViewModel(KnowledgeViewModel::class.java)
    fun obtainWeChatModel(): WeChatViewModel = initViewModel(WeChatViewModel::class.java)
    fun obtainDetailModel() = initViewModel(KnowledgeDetailViewModel::class.java)
    fun obtainNavigationModel() = initViewModel(NavigationViewModel::class.java)
    fun obtainProjectModel() = initViewModel(ProjectViewModel::class.java)
    fun obtainProjectDetailModel() = initViewModel(ProjectDetailViewModel::class.java)

    override fun onDestroy() {
        super.onDestroy()
        homeFragment = null
        knowledgeFragment = null
        weChatFragment = null
        navigationFragment = null
        projectFragment = null
        mainCollectFragment = null
    }
}
