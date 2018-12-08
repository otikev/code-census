package com.otikev.codebasecensus

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.codebasecensus.core.CloneCallback
import com.codebasecensus.core.CodebaseCensus
import java.net.URI

class MainActivity : AppCompatActivity() {

    var codebaseCensus : CodebaseCensus = CodebaseCensus()

    var cloneCallback : CloneCallback = CloneCallbackImpl()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var uri : URI = URI.create("https://github.com/eclipse/jgit.git")
        codebaseCensus.cloneRepo(uri, cloneCallback)
    }

}
