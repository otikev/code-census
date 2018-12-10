package com.otikev.codecensus.repos

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.otikev.codecensus.R


class ReposActivity : AppCompatActivity(), ReposContract.View {

    private val REQUEST_WRITE_STORAGE = 112

    lateinit var presenter: ReposContract.Presenter
    lateinit var adapter : RepoAdapter
    lateinit var repoRecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repos)

        presenter = ReposPresenter(this)
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
        repoRecyclerView = findViewById(R.id.repoRecyclerView)
        repoRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_WRITE_STORAGE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.onPermissionGranted()
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

    override fun setupList(items: ArrayList<RepoItem>) {
        adapter = RepoAdapter(this, items)
        repoRecyclerView.adapter = adapter
    }
}