package com.codebasecensus.core;

import java.net.URI;

/**
 * Created by kevin on 08/12/18 at 11:58
 */
public interface Codebase {
    void cloneRepo(URI uri, CloneCallback callback);

    void cloneRepo(URI uri, String branch, CloneCallback callback);
}
