package com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.View
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.constant.Constants
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
import me.yokeyword.fragmentation.SupportActivity

class MainActivity : SupportActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
    }

    private fun initData() {
        setSupportActionBar(common_toolbar)
        val actionBar = supportActionBar!!
        actionBar.setDisplayShowTitleEnabled(false)
        StatusBarUtil.setStatusColor(window, ContextCompat.getColor(this, R.color.main_status_bar_blue), 1.0f)
        common_toolbar.setNavigationOnClickListener { onBackPressedSupport() }
        initBottomNavigation()
        initDrawLayout()
        initNavigationItem()
        HomeFragment.newInstance().also {
            replaceFragmentInActivity(it,R.id.fl_page,Constants.TYPE_HOME)
            common_toolbar_title_tv.text = Constants.TYPE_HOME
        }
    }

    private fun initBottomNavigation(){
        bottom_navigation_view.run {
            labelVisibilityMode = 1
            setOnNavigationItemSelectedListener(onNavigationItemReselectedListener)
        }
    }

    private fun initDrawLayout(){
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

    override fun onBackPressedSupport() {
        if(drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressedSupport()
        }
    }

    private val onNavigationItemReselectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                return@OnNavigationItemSelectedListener when (item.itemId) {
                    R.id.tab_home_pager -> {
                        if(null == supportFragmentManager.findFragmentByTag(Constants.TYPE_HOME)){
                            HomeFragment.newInstance().also {
                                replaceFragmentInActivity(it,R.id.fl_page,Constants.TYPE_HOME)
                                common_toolbar_title_tv.text = Constants.TYPE_HOME
                            }
                        }
                        true
                    }
                    R.id.tab_knowledge_hierarchy -> {
                        if(null == supportFragmentManager.findFragmentByTag(Constants.TYPE_KNOWLEDGE)){
                            KnowledgeFragment.newInstance().also {
                                replaceFragmentInActivity(it,R.id.fl_page,Constants.TYPE_KNOWLEDGE)
                                common_toolbar_title_tv.text = Constants.TYPE_KNOWLEDGE
                            }
                        }
                        true
                    }
                    R.id.tab_weChat ->{
                        if(null == supportFragmentManager.findFragmentByTag(Constants.TYPE_WECHAT)){
                            WechatFragment.newInstance().also {
                                replaceFragmentInActivity(it,R.id.fl_page,Constants.TYPE_WECHAT)
                                common_toolbar_title_tv.text = Constants.TYPE_WECHAT
                            }
                        }
                        true
                    }
                    R.id.tab_navigation -> {
                        if(null == supportFragmentManager.findFragmentByTag(Constants.TYPE_NAVIGATION)){
                            NavigationFragment.newInstance().also {
                                replaceFragmentInActivity(it,R.id.fl_page,Constants.TYPE_NAVIGATION)
                                common_toolbar_title_tv.text = Constants.TYPE_NAVIGATION
                            }
                        }
                        true
                    }
                    else -> {
                        if(null == supportFragmentManager.findFragmentByTag(Constants.TYPE_PROJECT)){
                            ProjectFragment.newInstance().also {
                                replaceFragmentInActivity(it,R.id.fl_page,Constants.TYPE_PROJECT)
                                common_toolbar_title_tv.text = Constants.TYPE_PROJECT
                            }
                        }
                        true
                    }
                }

            }

    private fun initNavigationItem(){
        navigation.menu.findItem(R.id.nav_item_my_collect).setOnMenuItemClickListener {
            if(null == supportFragmentManager.findFragmentByTag(Constants.TYPE_COLLECT)){
                CollectFragment.newInstance().also {
                    replaceFragmentInActivity(it,R.id.fl_page,Constants.TYPE_COLLECT)
                }
            }
            fab.visibility = View.INVISIBLE
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
            if(null == supportFragmentManager.findFragmentByTag(Constants.TYPE_HOME)){
                HomeFragment.newInstance().also {
                    replaceFragmentInActivity(it,R.id.fl_page,Constants.TYPE_HOME)
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

    private fun closeDrawer(){
        if(drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START)
        }
    }

    fun obtainHomeModel():HomeViewModel = initViewModel(HomeViewModel::class.java)
}
