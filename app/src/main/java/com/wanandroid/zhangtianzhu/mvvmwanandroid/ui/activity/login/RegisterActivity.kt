package com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.login

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.bean.event.LoginEvent
import com.wanandroid.zhangtianzhu.mvvmwanandroid.databinding.ActivityRegisterBinding
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.MainActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.DialogUtil
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.SnackUtils
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.initViewModel
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.login.RegisterViewModel
import kotlinx.android.synthetic.main.activity_register.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.startActivity

class RegisterActivity : BaseActivity() {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding: ActivityRegisterBinding
    private val mDialog by lazy { DialogUtil.getWaitDialog(this, getString(R.string.register_ing)) }

    override fun initData() {
        binding = getDataBinding() as ActivityRegisterBinding
        viewModel = obtainViewModel()
        binding.viewmodel = viewModel
        setListener()
    }

    override fun getLayoutId(): Int = R.layout.activity_register

    override fun onViewSaveInstance(savedInstanceState: Bundle?) {
    }


    private fun setListener() {
        binding.onClickListener = onClickListener
    }

    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.register_toolbar ->
                finish()
            R.id.register_btn ->
                register()
            R.id.tv_login ->
                startActivity<LoginActivity>()
        }
    }

    private fun register() {
        var showSuccess = true
        var showMsg = true
        if (validate()) {
            mDialog.show()
            binding.viewmodel?.start(register_account_edit.text.toString(),
                    register_password_edit.text.toString(),
                    register_repassword_edit.text.toString())
            binding.viewmodel?.success?.observe(this, Observer {
                if (it!! && showSuccess) {
                    if(mDialog.isShowing){
                        mDialog.dismiss()
                    }
                    startActivity<MainActivity>()
                    finish()
                    showSuccess = false
                    isLogin = true
                    isFirstIn = false
                }
            })
            binding.viewmodel?.loginData?.observe(this, Observer { loginData ->
                user = loginData!!.username
                EventBus.getDefault().post(LoginEvent(true))
            })
            //TODO LiveData setValue中方法设置了避免重复发送数据通知观察者
            binding.viewmodel?.showErrorMsg?.observe(this, Observer {
                if (it!! && showMsg) {
                    viewModel.errorMsgText.observe(this, Observer { msg ->
                        SnackUtils.showSnackBar(this@RegisterActivity, msg!!)
                    })
                    showMsg = false
                }
            })
        }
    }

    private fun validate(): Boolean {
        var valid = true
        val username: String = register_account_edit.text.toString()
        val password: String = register_password_edit.text.toString()
        val repassword: String = register_repassword_edit.text.toString()

        if (username.isEmpty()) {
            register_account_edit.error = getString(R.string.username_not_empty)
            valid = false
        }
        if (password.isEmpty()) {
            register_password_edit.error = getString(R.string.password_not_empty)
            valid = false
        }
        if (password != repassword) {
            register_repassword_edit.error = getString(R.string.password_not_equals_repassword)
            valid = false
        }
        if (repassword.isEmpty()) {
            register_repassword_edit.error = getString(R.string.repassword_not_empty)
            valid = false
        }
        return valid

    }

    private fun obtainViewModel(): RegisterViewModel = initViewModel(RegisterViewModel::class.java)

}
