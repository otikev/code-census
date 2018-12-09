package com.otikev.codecensus

import android.util.Log
import com.codecensus.core.*
import com.codecensus.core.callbacks.AnalyzeCallback
import com.codecensus.core.callbacks.SetupCallback
import java.net.URI

/**
 * Created by kevin on 08/12/18 at 20:25
 */
class MainPresenter(var view : MainContract.View) :MainContract.Presenter {

    var repo : String = "https://github.com/otikev/codebase-census.git"
    lateinit var codebaseCensus : CodeCensus

    override fun onViewInit() {
        view.bindViews()
        if(view.hasWriteExternalStoragePermission()){
            setup()
        }else{
            view.requestWriteExternalStoragePermission()
        }
    }

    override fun setup() {

        view.setRepoName(repo)

        var uri : URI = URI.create(repo)

        codebaseCensus = CodeCensus(GIT(uri))
        codebaseCensus.setup(object : SetupCallback {
            override fun setupSuccess() {
                Log.d(javaClass.simpleName,"Setup success")
                codebaseCensus.analyzeTotalFileCount(object : AnalyzeCallback {
                    override fun onFinished(model: ResultModel) {
                        view.setTotalFiles(model.count)
                        Log.d(javaClass.simpleName,"TOTAL FILES : "+model.count)
                    }

                    override fun onFailure(reason: String) {
                        Log.e(javaClass.simpleName, "Failed : $reason")
                    }
                })
            }

            override fun setupFailed(error: String) {
                Log.d(javaClass.simpleName,"Setup failed")
            }
        })
    }
}