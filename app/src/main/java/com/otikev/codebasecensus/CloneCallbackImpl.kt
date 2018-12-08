package com.otikev.codebasecensus

import com.codebasecensus.core.CloneCallback
import timber.log.Timber

/**
 * Created by kevin on 08/12/18 at 13:28
 */
class CloneCallbackImpl: CloneCallback {
    override fun cloneSuccess() {
        Timber.i("clone success!")
    }

    override fun cloneFailure(reason: String?) {
        Timber.e("clone failed! $reason")
    }
}