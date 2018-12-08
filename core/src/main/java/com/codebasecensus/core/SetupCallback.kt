package com.codebasecensus.core

/**
 * Created by kevin on 08/12/18 at 15:53
 */
interface SetupCallback {
    fun setupSuccess()
    fun setupFailed(error: String)
}