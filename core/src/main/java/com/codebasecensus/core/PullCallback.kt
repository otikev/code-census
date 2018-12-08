package com.codebasecensus.core

/**
 * Created by kevin on 08/12/18 at 15:43
 */
interface PullCallback {
    fun pullSuccess()

    fun pullFailed(error : String)
}