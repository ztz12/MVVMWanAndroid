package com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.home

import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseSwipeBackActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.databinding.ActivityAboutUsBinding
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.ElasticOutInterpolator
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.content_about.*

class AboutUsActivity : BaseSwipeBackActivity() {

    private lateinit var binding: ActivityAboutUsBinding

    private var mThemeListener: View.OnClickListener? = null

    override fun getLayoutId(): Int = R.layout.activity_about_us

    override fun initData() {
        binding = getDataBinding() as ActivityAboutUsBinding
        setSupportActionBar(about_us_toolbar)
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, about_us_toolbar)
        binding.aboutUsToolbar.setNavigationOnClickListener { onBackPressedSupport() }
        init()
    }

    private fun init() {
        showAboutContent()
        setSmartRefreshLayout()

        //进入界面自动刷新
        binding.aboutUsRefreshLayout.autoRefresh()

        //点击悬浮按钮刷新
        binding.aboutUsFab.setOnClickListener { about_us_refresh_layout.autoRefresh() }

        //监听AppBarLayout 开启与关闭 给FlyView 和ActionButton 设置隐藏动画
        binding.aboutUsAppBar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var misAppbarExpand = true

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                val scrollRange = appBarLayout.totalScrollRange
                val fraction = 1f * (scrollRange + verticalOffset) / scrollRange
                val minFraction = 0.1
                val maxFraction = 0.8
                if (about_us_content == null || about_us_fab == null || about_us_fly_view == null) {
                    return
                }
                if (fraction < minFraction && misAppbarExpand) {
                    misAppbarExpand = false
                    about_us_fab.animate().scaleX(0f).scaleY(0f)
                    about_us_fly_view.animate().scaleX(0f).scaleY(0f)
                    val animator = ValueAnimator.ofInt(about_us_content.paddingTop, 0)
                    animator.duration = 300
                    animator.addUpdateListener { animation -> about_us_content.setPadding(0, animation.animatedValue as Int, 0, 0) }
                    animator.start()
                }
                if (fraction > maxFraction && !misAppbarExpand) {
                    misAppbarExpand = true
                    about_us_fab.animate().scaleX(1f).scaleY(1f)
                    about_us_fly_view.animate().scaleX(1f).scaleY(1f)
                    val animator = ValueAnimator.ofInt(about_us_content.paddingTop, DensityUtil.dp2px(25f))
                    animator.duration = 300
                    animator.addUpdateListener { animation -> about_us_content.setPadding(0, animation.animatedValue as Int, 0, 0) }
                    animator.start()
                }
            }
        })
    }

    private fun showAboutContent() {
        aboutContent.text = Html.fromHtml(getString(R.string.about_content))
        aboutContent.movementMethod = LinkMovementMethod.getInstance()
        try {
            val version = getString(R.string.app_name) + "\nV" + packageManager.getPackageInfo(packageName, 0).versionName
            aboutVersion.text = version
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setSmartRefreshLayout() {
        //绑定场景与纸飞机
        binding.aboutUsFlyRefresh.setUp(about_us_mountain, about_us_fly_view)
        binding.aboutUsRefreshLayout.run {
            setReboundInterpolator(ElasticOutInterpolator())
            setReboundDuration(800)
            setOnRefreshListener {
                updateTheme()
                finishRefresh(1000)
            }
        }
        //设置让toolbar 与appBarLayout 滚动同步
        binding.aboutUsRefreshLayout.setOnMultiPurposeListener(object : SimpleMultiPurposeListener() {
            override fun onHeaderPulling(header: RefreshHeader?, percent: Float, offset: Int, headerHeight: Int, extendHeight: Int) {
                if (about_us_toolbar == null || about_us_app_bar == null) {
                    return
                }
                about_us_app_bar.translationY = offset.toFloat()
                about_us_toolbar.translationY = -offset.toFloat()
            }

            override fun onHeaderReleasing(header: RefreshHeader?, percent: Float, offset: Int, footerHeight: Int, extendHeight: Int) {
                if (about_us_app_bar == null || about_us_toolbar == null) {
                    return
                }
                about_us_toolbar.translationY = offset.toFloat()
                about_us_app_bar.translationY = offset.toFloat()
            }
        })
    }

    private fun updateTheme() {
        if (mThemeListener == null) {
            mThemeListener = object : View.OnClickListener {
                var index = 0
                val ids = intArrayOf(R.color.colorPrimary, android.R.color.holo_green_light,
                        android.R.color.holo_red_light, android.R.color.holo_orange_light,
                        android.R.color.holo_blue_bright)

                override fun onClick(p0: View?) {
                    val color = ContextCompat.getColor(applicationContext, ids[index % ids.size])
                    about_us_refresh_layout.setPrimaryColors(color)
                    about_us_fab.run {
                        setBackgroundColor(color)
                        backgroundTintList = ColorStateList.valueOf(color)
                    }
                    about_us_toolbar_layout.setContentScrimColor(color)
                    index++
                }

            }
        }
        mThemeListener?.onClick(null)
    }

    override fun onViewSaveInstance(savedInstanceState: Bundle?) {
    }

}
