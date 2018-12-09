package com.codecensus.core

import com.codecensus.core.callbacks.AnalyzeCallback
import com.codecensus.core.callbacks.CloneCallback
import com.codecensus.core.callbacks.PullCallback
import com.codecensus.core.callbacks.SetupCallback
import org.eclipse.jgit.revwalk.RevCommit
import java.io.File
import java.util.*


/**
 * Created by kevin on 08/12/18 at 14:01
 */
class CodeCensus(var git: GIT) : Codebase {

    var initialized : Boolean = false

    override fun setup(callback: SetupCallback) {
        if(git.repoExists()){
            git.pull(object: PullCallback {
                override fun pullSuccess() {
                    initialized = true
                    callback.setupSuccess()
                }

                override fun pullFailed(error: String) {
                    callback.setupFailed(error)
                }
            })
        }else{
            git.cloneRepo(object: CloneCallback {
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
        val commits : Iterable<RevCommit> = git.log()

        var contMap : HashMap<String,Int> = HashMap()//Total contributions per person
        var timeMap : Map<Date,Int> = HashMap()//Contributions by date

        for (commit in commits){
            if(contMap.containsKey(commit.name)){
                contMap[commit.name]?.plus(1)?.let { contMap.put(commit.name, it) }
            }else{
                contMap[commit.name] = 1
            }
            //TODO: gather daily contribution
        }
    }

    override fun analyzeTotalFileCount(callback: AnalyzeCallback) {
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

    private fun countFiles(dir : File) : Int{
        var size = 0
        for (file in dir.listFiles()!!) {
            size += if (file.isFile) {
                1
            } else {
                countFiles(file)
            }
        }
        return size
    }
}