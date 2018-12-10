package com.otikev.codecensus.repos

/**
 * Created by kevin on 08/12/18 at 20:25
 */
class ReposPresenter(var view : ReposContract.View) : ReposContract.Presenter {

    override fun onViewInit() {
        view.bindViews()
        if(view.hasWriteExternalStoragePermission()){
            onPermissionGranted()
        }else{
            view.requestWriteExternalStoragePermission()
        }
    }

    override fun onPermissionGranted() {
        view.setupList(buildDummyRepoData())//TODO: replace with real data
    }

    private fun buildDummyRepoData(): ArrayList<RepoItem>{
        var list : ArrayList<RepoItem> = ArrayList()
        for(i in 0..7){
            var trend = buildDummyTrendData()
            var commitTrend = CommitTrend(trend)
            list.add(RepoItem("https://github.com/otikev/code-census.git",commitTrend))
        }
        return list
    }

    private fun buildDummyTrendData(): ArrayList<Int>{
        var trend : ArrayList<Int> = ArrayList()

        for (i in 0..30){
            val commits = getRandomNumberBetweenRange(0,20)
            trend.add(commits)
        }
        return trend
    }

    private fun getRandomNumberBetweenRange(min: Int, max: Int): Int {
        return (Math.random() * (max - min + 1) + min).toInt()
    }
}