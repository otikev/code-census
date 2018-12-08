package com.codebasecensus.core

/**
 * Created by kevin on 08/12/18 at 16:19
 */
interface AnalyzeCallback {
    fun onFinished(model : ResultModel)
    fun onFailure(reason : String)
}