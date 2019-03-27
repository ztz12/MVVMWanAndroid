package com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.knowledge

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.knowledge.KnowledgePageAdapter
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.constant.Constants
import com.wanandroid.zhangtianzhu.mvvmwanandroid.databinding.ActivityKnowledgeListBinding
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.KnowledgeData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.KnowledgeTreeData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.fragment.knowledge.KnowledgeDetailFragment
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.DialogUtil
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_knowledge_list.*

class KnowledgeListActivity : BaseActivity() {

    private lateinit var binding: ActivityKnowledgeListBinding

    private var knowledgeData = mutableListOf<KnowledgeData>()

    private var toolbarTitle: String? = ""

    private val mAdapter:KnowledgePageAdapter by lazy { KnowledgePageAdapter(knowledgeData,supportFragmentManager) }

    override fun getLayoutId(): Int = R.layout.activity_knowledge_list

    override fun initData() {
        binding = getDataBinding() as ActivityKnowledgeListBinding
        intent?.extras.let {
            toolbarTitle = it?.getString(Constants.CONTENT_TITLE_KEY,"")
            it?.getSerializable(Constants.CONTENT_DATA_KEY)?.let {
                val data = it as KnowledgeTreeData
                data.children.let {child ->
                    knowledgeData.addAll(child)
                }
            }
        }
        init()
    }

    private fun init(){
        binding.toolbar.run {
            title = toolbarTitle
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        StatusBarUtil.setStatusColor(window, ContextCompat.getColor(this, R.color.main_status_bar_blue), 1.0f)
        binding.toolbar.setNavigationOnClickListener { onBackPressedSupport() }
        binding.vpKnowledge.run {
            adapter = mAdapter
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_knowledge))
            offscreenPageLimit = knowledgeData.size
        }

        binding.tabKnowledge.run {
            setupWithViewPager(vp_knowledge)
            addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(vp_knowledge))
            addOnTabSelectedListener(onTabSelectedListener)
        }
        binding.fabKnowledge.setOnClickListener(fabOnClickListener)
    }

    private val fabOnClickListener = View.OnClickListener {
        if(mAdapter.count==0){
            return@OnClickListener
        }

        val fragment = mAdapter.getItem(vp_knowledge.currentItem) as KnowledgeDetailFragment
        fragment.scrollTop()
    }

    private val onTabSelectedListener = object : TabLayout.OnTabSelectedListener{
        override fun onTabReselected(p0: TabLayout.Tab?) {

        }

        override fun onTabUnselected(p0: TabLayout.Tab?) {
        }

        override fun onTabSelected(p0: TabLayout.Tab?) {
            p0.let {
                vp_knowledge.setCurrentItem(it!!.position,false)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_share_content, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_share -> {
                Intent().run {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT,
                            getString(
                                    R.string.share_type_url,
                                    getString(R.string.app_name),
                                    knowledgeData[tab_knowledge.selectedTabPosition].name,
                                    knowledgeData[tab_knowledge.selectedTabPosition].id.toString()
                            ))
                    type = Constants.CONTENT_SHARE_TYPE
                    startActivity(Intent.createChooser(this, getString(R.string.share)))
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewSaveInstance(savedInstanceState: Bundle?) {
    }

}
