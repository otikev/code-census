package com.codebasecensus.core;

/**
 * Created by kevin on 08/12/18 at 12:39
 */
public interface CloneCallback {
    void cloneSuccess();

    void cloneFailure(String reason);
}
