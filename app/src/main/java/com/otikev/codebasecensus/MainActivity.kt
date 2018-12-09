package com.otikev.codebasecensus

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.codebasecensus.core.*
import java.net.URI


class MainActivity : AppCompatActivity(), MainContract.View {

    private val REQUEST_WRITE_STORAGE = 112

    lateinit var presenter: MainContract.Presenter
    lateinit var txtRepoName: TextView
    lateinit var txtTotalFiles: TextView
    lateinit var txtContributors: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(this)
        presenter.onViewInit()
    }

    override fun hasWriteExternalStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun requestWriteExternalStoragePermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_WRITE_STORAGE
        )
    }

    override fun bindViews() {
        txtRepoName = findViewById(R.id.txtRepoName)
        txtTotalFiles = findViewById(R.id.txtTotalFiles)
        txtContributors = findViewById(R.id.txtContributors)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_WRITE_STORAGE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.setup()
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

    override fun setRepoName(name: String) {
        runOnUiThread {
            txtRepoName.text = name
        }
    }

    override fun setTotalFiles(count: Int) {
        runOnUiThread {
            txtTotalFiles.text = count.toString()
        }
    }

    override fun setContributors(count: Int) {
        runOnUiThread {
            txtContributors.text = count.toString()
        }
    }
}