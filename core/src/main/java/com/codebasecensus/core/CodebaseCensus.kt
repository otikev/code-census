package com.codebasecensus.core

import java.io.File
import org.eclipse.jgit.storage.file.FileRepositoryBuilder



/**
 * Created by kevin on 08/12/18 at 14:01
 */
class CodebaseCensus(var git: GIT) : Codebase {

    var initialized : Boolean = false

    override fun setup(callback: SetupCallback) {
        if(git.repoExists()){
            git.pull(object: PullCallback{
                override fun pullSuccess() {
                    initialized = true
                    callback.setupSuccess()
                }

                override fun pullFailed(error: String) {
                    callback.setupFailed(error)
                }
            })
        }else{
            git.cloneRepo("master",object: CloneCallback{
                override fun cloneSuccess() {
                    initialized = true
                    callback.setupSuccess()
                }

                override fun cloneFailure(reason: String) {
                    callback.setupFailed(reason)
                }
            })
        }
    }

    override fun contributors() {

    }

    override fun analizeTotalFileCount(callback: AnalyzeCallback) {
        if(!initialized){
            callback.onFailure("Not initialized, call setup() first")
            return
        }

        val dirString = git.getRepoDirectory()

        Thread(Runnable {
            val count : Int = countFiles(File(dirString))
            val model = ResultModel(AnalysisType.FILE_COUNT,count)
            callback.onFinished(model)
        }).start()
    }

    private fun countFiles(file : File) : Int{
        var size = 0
        for (file in file.listFiles()!!) {
            size += if (file.isFile()) {
                1
            } else {
                countFiles(file)
            }
        }
        return size
    }
}