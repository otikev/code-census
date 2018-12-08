package com.codebasecensus.core;

/**
 * Created by kevin on 08/12/18 at 11:58
 */
interface Codebase {
    fun setup(callback: SetupCallback)

    fun analizeTotalFileCount(callback: AnalyzeCallback)
}