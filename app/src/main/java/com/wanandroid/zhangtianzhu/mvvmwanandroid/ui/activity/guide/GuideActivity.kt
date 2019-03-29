package com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.guide

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.KeyEvent
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.databinding.ActivityGuideBinding
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.MainActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.login.LoginActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.widget.CountDownView
import kotlinx.android.synthetic.main.activity_guide.*
import org.jetbrains.anko.startActivity

class GuideActivity : BaseActivity() {

    private val SCALE_SIZE = 1.3f

    private lateinit var binding: ActivityGuideBinding

    override fun getLayoutId(): Int = R.layout.activity_guide

    override fun initData() {
        binding = getDataBinding() as ActivityGuideBinding
        animateImage()
        initCountDownView()
        binding.cdvTime.setOnClickListener {
            cdv_time.stop()
            startMainActivity()
            finish()
        }
    }

    private fun animateImage() {
        val animatorX = ObjectAnimator.ofFloat(iv_guide, "scaleX", 1f, SCALE_SIZE)
        val animatorY = ObjectAnimator.ofFloat(iv_guide, "scaleY", 1f, SCALE_SIZE)
        val animatorSet = AnimatorSet()
        animatorSet.setDuration(3000).play(animatorX).with(animatorY)
        animatorSet.start()
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                startMainActivity()
            }
        })
    }

    private fun startMainActivity() {
        if (isFirstIn) {
            startActivity<LoginActivity>()
        } else {
            startActivity<MainActivity>()
        }
        finish()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        //屏蔽返回键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun initCountDownView() {
        binding.cdvTime.run {
            setTime(3)
            start()
            setOnLoadingFinishListener(object : CountDownView.OnLoadingFinishListener {
                override fun finish() {
                    startMainActivity()
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.cdvTime.isShown) {
            binding.cdvTime.stop()
        }
    }

    override fun onViewSaveInstance(savedInstanceState: Bundle?) {
    }
}
