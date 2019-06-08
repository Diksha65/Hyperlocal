package com.example.hyperlocal.navigationactivity

import android.Manifest
import android.os.Bundle
import com.example.hyperlocal.base.BaseActivity
import com.example.hyperlocal.R
import com.karumi.dexter.Dexter
import kotlinx.android.synthetic.main.activity_manage_permission.*
import com.karumi.dexter.PermissionToken
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import com.example.hyperlocal.MainActivity
import com.example.hyperlocal.extensions.isPermissionGranted
import com.example.hyperlocal.extensions.openSettingsDialog
import com.example.hyperlocal.extensions.requestSinglePermission
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener


class ManagePermissionActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_permission)

        assert(supportActionBar != null)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Manage Permission"

        setLocationSwitch()

        locationSwitch.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked)
                requestSinglePermission(this,this, Manifest.permission.ACCESS_FINE_LOCATION)
            else
                openSettingsDialog(this, this)
            setLocationSwitch()
        }
    }

    private fun setLocationSwitch() {
        locationSwitch.isChecked = isPermissionGranted(this, Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAndStart(MainActivity::class.java)
    }

    override fun onResume() {
        super.onResume()
        setLocationSwitch()
    }
}