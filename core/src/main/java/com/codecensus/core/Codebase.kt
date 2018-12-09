package com.codecensus.core;

import com.codecensus.core.callbacks.AnalyzeCallback
import com.codecensus.core.callbacks.SetupCallback

/**
 * Created by kevin on 08/12/18 at 11:58
 */
interface Codebase {
    fun setup(callback: SetupCallback)

    fun analyzeTotalFileCount(callback: AnalyzeCallback)

    fun contributors()
}