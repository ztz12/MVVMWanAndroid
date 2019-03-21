package com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.login

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.data.source.login.LoginSource
import com.wanandroid.zhangtianzhu.mvvmwanandroid.http.LoginData
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.Injection

class LoginViewModel constructor(application: Application):AndroidViewModel(application){
    private val _loginData = MutableLiveData<LoginData>()
    val loginData: LiveData<LoginData>
        get() = _loginData

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean>
        get() = _success
    private val _showError = MutableLiveData<Boolean>()
    val showError: LiveData<Boolean>
        get() = _showError

    private val _showErrorMsg = MutableLiveData<Boolean>()
    val showErrorMsg: LiveData<Boolean>
        get() = _showErrorMsg

    private val _errorMsgText = MutableLiveData<String>()
    val errorMsgText: LiveData<String>
        get() = _errorMsgText


    fun start(username:String,password:String){
        _success.value = false
        _showErrorMsg.value = false
        _showError.value = false
        Injection.provideLoginSource().loginWanAndroid(username,password,object :LoginSource.LoadLoginCallback{
            override fun getLoginData(loginData: LoginData) {
                _success.value = true
                _showError.value = false
                _showErrorMsg.value = false
                _loginData.value = loginData
            }

            override fun showErrorMsg(msg: String) {
                _showErrorMsg.value = true
                _success.value = false
                _showError.value = false
                _errorMsgText.value = msg
            }

            override fun showError(t: Throwable) {
                _showError.value = true
                _success.value = false
                _showErrorMsg.value = false
            }

        })
    }
}