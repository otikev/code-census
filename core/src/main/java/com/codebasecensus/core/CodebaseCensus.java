package com.codebasecensus.core;

import android.os.Environment;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.Transport;

import java.io.File;
import java.net.URI;
import java.util.Arrays;

/**
 * Created by kevin on 08/12/18 at 11:31
 */
public class CodebaseCensus implements Codebase{
    /**
     *
     * @param uri e.g "https://github.com/eclipse/jgit.git"
     * @param callback
     */
    @Override
    public void cloneRepo(URI uri, CloneCallback callback) {
        cloneRepo(uri, "master",callback);
    }

    @Override
    public void cloneRepo(URI uri, String branch, CloneCallback callback) {
        try {
            Git.cloneRepository()
                    .setURI(uri.toASCIIString())
                    .setDirectory(new File(getRepoDirectory(uri)))
                    .setBranchesToClone(Arrays.asList("refs/heads/"+branch))
                    .setBranch("refs/heads/"+branch)
                    .call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    String getRepoDirectory(URI uri){
        return Environment.getExternalStorageDirectory().getPath()+"/codebase-census/"+folderNameFromGiturl(uri);
    }

    String folderNameFromGiturl(URI uri){
        String url = uri.toASCIIString();

        String[] exploded = url.split("/");

        return exploded[exploded.length-1];
    }
}