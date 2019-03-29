package com.wanandroid.zhangtianzhu.mvvmwanandroid.constant

import com.wanandroid.zhangtianzhu.mvvmwanandroid.R
import com.wanandroid.zhangtianzhu.mvvmwanandroid.app.WanAndroidApplication
import com.wanandroid.zhangtianzhu.mvvmwanandroid.util.Preference
import java.io.File

object Constants {

    const val BASE_URl = "https://www.wanandroid.com/"

    const val USERNAME = "username"

    const val ISLOGIN = "isLogin"

    const val ISFIRSTIN = "isFirstIn"

    const val SAVE_LOGIN_KEY = "user/login"

    const val SAVE_REGISTER_KEY = "user/register"

    const val COLLECTIONS_WEBSITE = "lg/collect"
    const val UNCOLLECTIONS_WEBSITE = "lg/uncollect"
    const val ARTICLE_WEBSITE = "article"
    const val TODO_WEBSITE = "lg/todo"

    const val SET_COOKIE_KEY = "set-cookie"
    const val COOKIE_NAME = "Cookie"
    private val PATCH_FILE: String = WanAndroidApplication.context.cacheDir.absolutePath + File.separator + "data"

    val PATCH_CACHE = "$PATCH_FILE/NetCache"

    const val DELAY_TIME: Long = 2000

    /**
     * Refresh theme color
     */
    const val BLUE_THEME = R.color.colorPrimary

    const val GREEN_THEME = android.R.color.holo_green_light

    const val RED_THEME = android.R.color.holo_red_light

    const val ORANGE_THEME = android.R.color.holo_orange_light

    const val ZERO = 0

    const val ONE = 1

    const val TWO = 2

    const val THREE = 3

    const val FOUR = 4

    const val TYPE_HOME = "首页"

    const val TYPE_KNOWLEDGE = "知识体系"

    const val TYPE_WECHAT = "公众号"

    const val TYPE_NAVIGATION = "导航"

    const val TYPE_PROJECT = "项目"

    const val TYPE_COLLECT = "收藏"

    const val PAGE_SIZE = 15

    const val ENABLE_PLACEHOLDERS = false

    /**
     * url key
     */
    const val CONTENT_URL_KEY = "url"
    /**
     * title key
     */
    const val CONTENT_TITLE_KEY = "title"
    /**
     * id key
     */
    const val CONTENT_ID_KEY = "id"
    /**
     * id key
     */
    const val CONTENT_CID_KEY = "cid"
    /**
     * share key
     */
    const val CONTENT_SHARE_TYPE = "text/plain"
    /**
     * content data key
     */
    const val CONTENT_DATA_KEY = "content_data"

    fun encodeCookie(cookies: List<String>): String {
        val sb = StringBuilder()
        val set = HashSet<String>()
        cookies
                .map { cookie ->
                    cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                }
                .forEach {
                    it.filterNot { set.contains(it) }.forEach { set.add(it) }
                }
        val it = set.iterator()
        while (it.hasNext()) {
            val cookie = it.next()
            sb.append(cookie).append(";")
        }
        val last = sb.lastIndexOf(";")
        if (sb.length - 1 == last) {
            sb.deleteCharAt(last)
        }
        return sb.toString()
    }

    fun saveCookie(url: String?, domain: String?, cookie: String) {
        url ?: return
        var spUrl by Preference(url, cookie)
        @Suppress("UNUSED_VALUE")
        spUrl = cookie

        domain ?: return
        var spDomain by Preference(domain, cookie)
        @Suppress("UNUSED_VALUE")
        spDomain = cookie

    }
}