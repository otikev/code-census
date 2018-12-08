package com.otikev.codebasecensus

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.codebasecensus.core.*
import java.net.URI


class MainActivity : AppCompatActivity() {

    private val REQUEST_WRITE_STORAGE = 112

    lateinit var codebaseCensus : CodebaseCensus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val hasPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

        if (!hasPermission) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_WRITE_STORAGE
            )
        }else{
            clone()
        }
    }

    fun clone(){
        var uri : URI = URI.create("https://github.com/otikev/codebase-census.git")

        codebaseCensus = CodebaseCensus(GIT(uri))
        codebaseCensus.setup(object : SetupCallback{
            override fun setupSuccess() {
                Log.d(javaClass.simpleName,"Setup success")
                codebaseCensus.analizeTotalFileCount(object : AnalyzeCallback{
                    override fun onFinished(model: ResultModel) {
                        Log.d(javaClass.simpleName,"TOTAL FILES : "+model.count)
                    }

                    override fun onFailure(reason: String) {
                        Log.e(javaClass.simpleName, "Failed : $reason")
                    }
                })
            }

            override fun setupFailed(error: String) {
                Log.d(javaClass.simpleName,"Setup failed")
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_WRITE_STORAGE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    clone()
                } else {
                    Toast.makeText(
                        this,
                        "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
