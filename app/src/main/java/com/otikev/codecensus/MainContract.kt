package com.otikev.codecensus

/**
 * Created by kevin on 08/12/18 at 20:20
 */
interface MainContract {
    interface View {
        fun bindViews()
        fun hasWriteExternalStoragePermission(): Boolean
        fun requestWriteExternalStoragePermission()
        fun setRepoName(name: String)
        fun setTotalFiles(count: Int)
        fun setContributors(count: Int)
    }

    interface Presenter {
        fun onViewInit()
        fun setup()
    }
}