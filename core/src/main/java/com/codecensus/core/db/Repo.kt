package com.codecensus.core.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ColumnInfo
import android.support.annotation.NonNull
import android.arch.persistence.room.PrimaryKey



/**
 * Created by kevin on 16/12/18 at 15:21
 */
@Entity(tableName = "repos")
class Repo {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    lateinit var id: Integer

    @NonNull
    @ColumnInfo(name = "url")
    lateinit var url: String
}