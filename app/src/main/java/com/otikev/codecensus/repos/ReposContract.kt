package com.otikev.codecensus.repos

/**
 * Created by kevin on 08/12/18 at 20:20
 */
interface ReposContract {
    interface View {
        fun bindViews()
        fun hasWriteExternalStoragePermission(): Boolean
        fun requestWriteExternalStoragePermission()
        fun setupList(items : ArrayList<RepoItem>)
    }

    interface Presenter {
        fun onViewInit()
        fun onPermissionGranted()
    }
}