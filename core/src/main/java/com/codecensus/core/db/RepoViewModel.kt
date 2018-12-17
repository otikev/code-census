package com.codecensus.core.db

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData



/**
 * Created by kevin on 16/12/18 at 20:14
 */
class RepoViewModel(application : Application) : AndroidViewModel(application) {

    private var mRepository: RepoRepository
    private var mAllRepos: LiveData<List<Repo>>

    init {
        mRepository = RepoRepository(application)
        mAllRepos = mRepository.getAllRepos()
    }

    fun getAllRepos(): LiveData<List<Repo>> {
        return mAllRepos
    }

    fun insert(repo: Repo) {
        mRepository.insert(repo)
    }
}