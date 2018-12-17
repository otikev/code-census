package com.codecensus.core.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.os.AsyncTask


/**
 * Created by kevin on 16/12/18 at 15:44
 */
@Database(entities = [Repo::class], version = 1)
abstract class CodecensusDatabase: RoomDatabase() {

    companion object {
        @Volatile
        private lateinit var INSTANCE: CodecensusDatabase

        fun getDatabase(context: Context): CodecensusDatabase {
            if (INSTANCE == null) {
                synchronized(CodecensusDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            CodecensusDatabase::class.java!!, "codecensus_database"
                        ).addCallback(sRoomDatabaseCallback)
                            .build()
                    }
                }
            }
            return INSTANCE
        }

        private val sRoomDatabaseCallback = object : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                PopulateDbAsync(INSTANCE).execute()
            }
        }
    }

    private class PopulateDbAsync internal constructor(db: CodecensusDatabase) : AsyncTask<Void, Void, Void>() {

        private var mDao: RepoDao

        init {
            mDao = db.repoDao()
        }

        override fun doInBackground(vararg params: Void): Void? {
            val default = "https://github.com/otikev/code-census.git"
            var data : LiveData<List<Repo>> = mDao.getRepoByUrl(default)
            if(data.value?.isEmpty()!!){
                var repo = Repo()
                repo.url = default
                mDao.insert(repo)
            }
            return null
        }
    }

    abstract fun repoDao(): RepoDao
}