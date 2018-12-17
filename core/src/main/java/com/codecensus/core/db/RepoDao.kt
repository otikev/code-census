package com.codecensus.core.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

/**
 * Created by kevin on 16/12/18 at 15:25
 */
@Dao
interface RepoDao {

    @Insert
    fun insert(repo: Repo)

    @Query("DELETE FROM repos")
    fun deleteAll()

    @Query("SELECT * from repos ORDER BY id ASC")
    fun getAllRepos(): LiveData<List<Repo>>

    @Query("SELECT * from repos WHERE url LIKE :url ORDER BY id ASC")
    fun getRepoByUrl(url: String): LiveData<List<Repo>>

}