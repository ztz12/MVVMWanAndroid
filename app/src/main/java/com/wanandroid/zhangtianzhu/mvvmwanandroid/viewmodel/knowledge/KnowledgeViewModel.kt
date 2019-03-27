package com.wanandroid.zhangtianzhu.mvvmwanandroid.viewmodel.knowledge

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.wanandroid.zhangtianzhu.mvvmwanandroid.paging.knowledge.KnowledgeDataRepository

class KnowledgeViewModel(application: Application) : AndroidViewModel(application) {
    val knowledgeViewModel = KnowledgeDataRepository.getInstance().getDataRepository()

    fun retry() {
        KnowledgeDataRepository.getInstance().getListingRepository()
    }

    fun refresh() {
        KnowledgeDataRepository.getInstance().getListingRepository()
    }
}