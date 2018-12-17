package com.codecensus.core.db

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask.execute
import android.os.AsyncTask







/**
 * Created by kevin on 16/12/18 at 15:56
 */
class RepoRepository(application : Application) {
    private var mRepoDao: RepoDao
    private var mAllRepos: LiveData<List<Repo>>

    init {
        var db : CodecensusDatabase = CodecensusDatabase.getDatabase(application)
        mRepoDao = db.repoDao()
        mAllRepos = mRepoDao.getAllRepos()
    }

    fun getAllRepos(): LiveData<List<Repo>> {
        return mAllRepos
    }

    fun insert(repo: Repo) {
        InsertAsyncTask(mRepoDao).execute(repo)
    }

    private class InsertAsyncTask internal constructor(private val mAsyncTaskDao: RepoDao) :
        AsyncTask<Repo, Void, Void>() {

        override fun doInBackground(vararg params: Repo): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }
}