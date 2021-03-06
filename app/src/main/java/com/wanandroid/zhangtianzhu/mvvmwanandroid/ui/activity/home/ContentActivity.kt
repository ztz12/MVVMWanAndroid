package com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseSwipeBackActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.constant.Constants
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.StatusBarUtil
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.getAgent
import kotlinx.android.synthetic.main.agent_container.*
import kotlinx.android.synthetic.main.toolbar.*


class ContentActivity : BaseSwipeBackActivity() {


    private var agentWeb: AgentWeb? = null
    private lateinit var shareTitle: String
    private lateinit var shareUrl: String
    private var shareId: Int = 0

    override fun getLayoutId(): Int {
        return R.layout.activity_content
    }

    override fun initData() {
        toolbar.run {
            title = getString(R.string.loading)
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        StatusBarUtil.setStatusColor(window, ContextCompat.getColor(this, R.color.main_status_bar_blue), 1.0f)
        toolbar.setNavigationOnClickListener { onBackPressedSupport() }
        intent.extras.let {
            shareTitle = it.getString(Constants.CONTENT_TITLE_KEY, "")
            shareUrl = it.getString(Constants.CONTENT_URL_KEY, "")
            shareId = it.getInt(Constants.CONTENT_ID_KEY, 0)
        }
        agentWeb = shareUrl.getAgent(this, fl_agent,
                LinearLayout.LayoutParams(-1, -1)
                , webChromeClient, webClient)
    }

    private val webClient = object : WebViewClient() {

    }

    private val webChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {

        }

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            title?.let {
                toolbar.title = it
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (agentWeb?.handleKeyEvent(keyCode, event)!!) {
            true
        } else {
            finish()
            super.onKeyDown(keyCode, event)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_content, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_share -> Intent().run {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT,
                        getString(
                                R.string.share_type_url,
                                getString(R.string.app_name),
                                shareTitle, shareUrl))
                type = Constants.CONTENT_SHARE_TYPE
                startActivity(Intent.createChooser(this, getString(R.string.share)))
            }
            R.id.action_browser -> {
                Intent().run {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse(shareUrl)
                    startActivity(this)
                }
            }
        }
        return true
    }

    /**
     * 跟随Activity生命周期，释放CPU更省电
     */
    override fun onResume() {
        agentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onPause() {
        agentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        agentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }

    override fun onViewSaveInstance(savedInstanceState: Bundle?) {

    }
}
