package com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.View
import android.widget.TextView
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.constant.Constants
import com.wanandroid.zhangtianzhu.mvvmwanandroid.databinding.ActivityMainBinding
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.login.LoginActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.home.CollectFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.home.HomeFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.knowledge.KnowledgeFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.navigation.NavigationFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.project.ProjectFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.wechat.WechatFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.StatusBarUtil
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.initViewModel
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.replaceFragmentInActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.home.HomeViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.common_toolbar.*
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity() {

    private var mType: String? = null

    private var homeFragment: HomeFragment? = null
    private var knowledgeFragment: KnowledgeFragment? = null
    private var weChatFragment: WechatFragment? = null
    private var navigationFragment: NavigationFragment? = null
    private var projectFragment: ProjectFragment? = null
    private var collectFragment: CollectFragment? = null

    private var tvUser: TextView? = null

    private var binding: ActivityMainBinding? = null

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onViewSaveInstance(savedInstanceState: Bundle?) {
    }

    override fun initData() {
        binding = getDataBinding() as ActivityMainBinding
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
                            WechatFragment.newInstance().also {
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
            if (null == supportFragmentManager.findFragmentByTag(Constants.TYPE_COLLECT)) {
                CollectFragment.newInstance().also {
                    collectFragment = it
                    replaceFragmentInActivity(it, R.id.fl_page, Constants.TYPE_COLLECT)
                }
            }
            bottom_navigation_view.visibility = View.INVISIBLE
            common_toolbar_title_tv.text = Constants.TYPE_COLLECT
            closeDrawer()
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
            closeDrawer()
            true
        }
    }

    private fun closeDrawer() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
    }

    fun obtainHomeModel(): HomeViewModel = initViewModel(HomeViewModel::class.java)

    override fun onDestroy() {
        super.onDestroy()
        homeFragment = null
        knowledgeFragment = null
        weChatFragment = null
        navigationFragment = null
        projectFragment = null
        collectFragment = null
    }
}
