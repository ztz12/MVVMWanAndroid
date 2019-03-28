package com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.login

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.base.BaseActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.bean.event.LoginEvent
import com.wanandroid.zhangtianzhu.mvvmwanandroid.databinding.ActivityLoginBinding
import com.wanandroid.zhangtianzhu.mvvmwanandroid.ui.activity.MainActivity
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.DialogUtil
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.SnackUtils
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.initViewModel
import com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.login.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.startActivity

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    private val mDialog by lazy { DialogUtil.getWaitDialog(this, getString(R.string.login_ing)) }

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun initData() {
        binding = getDataBinding() as ActivityLoginBinding
        viewModel = obtainViewModel()
        binding.viewmodel = viewModel
        binding.onClickListener = onClickListener
    }

    override fun onViewSaveInstance(savedInstanceState: Bundle?) {
    }

    private val onClickListener = View.OnClickListener {
        when (it.id) {
            R.id.login_toolbar ->
                finish()
            R.id.login_btn ->
                login()
            R.id.tv_create ->
                startActivity<RegisterActivity>()
        }
    }

    private fun login() {
        var showSuccess = true
        var showMsg = true
        if (validate()) {
            mDialog.show()
            binding.viewmodel?.start(login_account_edit.text.toString(),
                    login_password_edit.text.toString())
            binding.viewmodel?.success?.observe(this, Observer {
                if (it!! && showSuccess) {
                    if(mDialog.isShowing){
                        mDialog.dismiss()
                    }
                    startActivity<MainActivity>()
                    finish()
                    showSuccess = false
                }
            })
            binding.viewmodel?.loginData?.observe(this, Observer { loginData ->
                user = loginData!!.username
                EventBus.getDefault().post(LoginEvent(true))
            })
            binding.viewmodel?.showErrorMsg?.observe(this, Observer {
                if (it!! && showMsg) {
                    viewModel.errorMsgText.observe(this, Observer { msg ->
                        SnackUtils.showSnackBar(this@LoginActivity, msg!!)
                    })
                    showMsg = false
                }
            })
        }
    }

    private fun validate(): Boolean {
        var valid = true
        val username: String = login_account_edit.text.toString()
        val password: String = login_password_edit.text.toString()

        if (username.isEmpty()) {
            login_account_edit.error = getString(R.string.username_not_empty)
            valid = false
        }
        if (password.isEmpty()) {
            login_password_edit.error = getString(R.string.password_not_empty)
            valid = false
        }
        return valid

    }

    private fun obtainViewModel(): LoginViewModel = initViewModel(LoginViewModel::class.java)
}
