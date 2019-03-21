package com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.adapter.RecyclerBindingAdapter
import com.wanandroid.zhangtianzhu.mvvmwanandroid.bean.User
import com.wanandroid.zhangtianzhu.mvvmwanandroid.databinding.ActivityRecyclerBindingBinding
import com.wanandroid.zhangtianzhu.mvvmwanandroid.widget.RecyclerItemDecoration
import kotlin.collections.ArrayList

class RecyclerBindingActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRecyclerBindingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_recycler_binding)
        initRecycler()
    }

    private fun initRecycler(){
        binding.rlBinding.run {
            layoutManager = LinearLayoutManager(this@RecyclerBindingActivity)
            adapter = RecyclerBindingAdapter(getListUser())
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(RecyclerItemDecoration(this@RecyclerBindingActivity))
        }
    }

    private fun getListUser():MutableList<User>{
        var userList:MutableList<User> = ArrayList()
        val user1 = User("yif","111")
        val user2 = User("ztz","22223")
        userList.add(user1)
        userList.add(user2)
        return userList
    }
}
