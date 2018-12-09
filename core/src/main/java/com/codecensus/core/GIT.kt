package com.codecensus.core

import android.os.Environment
import android.util.Log
import com.codecensus.core.callbacks.CloneCallback
import com.codecensus.core.callbacks.PullCallback
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.errors.GitAPIException
import org.eclipse.jgit.api.errors.JGitInternalException
import org.eclipse.jgit.lib.ProgressMonitor
import java.io.File
import java.net.URI
import java.util.*
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.internal.storage.file.FileRepository




/**
 * Created by kevin on 08/12/18 at 15:25
 */
class GIT(var uri: URI) {

    var TAG : String = javaClass.simpleName

    var BRANCH : String = "refs/heads/master"

    lateinit var git : Git

    fun repoExists(): Boolean {
        val folder = File(uri?.let { getRepoDirectory() })
        return folder.exists()
    }

    fun pull(callback : PullCallback?){
        git = Git.open(File(uri?.let { getRepoDirectory() }))
        Thread(Runnable {
            val pullCmd = git.pull()
            pullCmd.setProgressMonitor(object : ProgressMonitor{
                override fun start(totalTasks: Int) {
                    Log.d(javaClass.simpleName,"start")
                }

                override fun beginTask(title: String, totalWork: Int) {
                    Log.d(javaClass.simpleName,"begin task")
                }

                override fun update(completed: Int) {
                    Log.d(javaClass.simpleName,"progress $completed")
                }

                override fun endTask() {
                    Log.d(javaClass.simpleName,"end task")
                    callback?.pullSuccess()
                }

                override fun isCancelled(): Boolean {
                    return false
                }

            }).call()
        }).start()
    }

    fun log() : Iterable<RevCommit>{
        val repository = FileRepository(getRepoDirectory()+"/.git")
        val treeName = BRANCH // tag or branch

        return git.log().add(repository.resolve(treeName)).call()
    }

    fun cloneRepo(callback: CloneCallback) {
        try {
            Log.i(javaClass.simpleName,"Cloning into " + (uri?.toASCIIString() ?: "null!!!"))

            val folder = File(getRepoDirectory())
            folder.mkdirs()

            Thread(Runnable {
                try{
                    Git.cloneRepository()
                        .setURI(uri?.toASCIIString())
                        .setDirectory(folder)
                        //.setCloneAllBranches(true)
                        .setBranchesToClone(Arrays.asList(BRANCH))
                        .setBranch(BRANCH)
                        .setProgressMonitor(object : ProgressMonitor {
                            override fun start(totalTasks: Int) {
                                Log.d(javaClass.simpleName,"start")
                            }

                            override fun beginTask(title: String, totalWork: Int) {
                                Log.d(javaClass.simpleName,"begin task")
                            }

                            override fun update(completed: Int) {
                                Log.d(javaClass.simpleName,"progress $completed")
                            }

                            override fun endTask() {
                                Log.d(javaClass.simpleName,"end task")
                                callback?.cloneSuccess()
                            }

                            override fun isCancelled(): Boolean {
                                return false
                            }
                        }).call()
                }catch (e : JGitInternalException){
                    Log.e(javaClass.simpleName,e.message,e)
                    e.message?.let { callback.cloneFailure(it) }
                }
            }).start()

        } catch (e: GitAPIException) {
            Log.e(javaClass.simpleName,e.message,e)
            e.message?.let { callback.cloneFailure(it) }
        }
    }

    fun getRepoDirectory(): String {
        return Environment.getExternalStorageDirectory().path + "/codebase-census/" + folderNameFromGitUrl()
    }

    private fun folderNameFromGitUrl(): String {
        val url = uri.toASCIIString()

        val exploded = url.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        val nameWithExtension = exploded[exploded.size - 1]

        return nameWithExtension.split(".")[0]
    }
}