package com.example.hyperlocal.navigationactivity

import android.os.Bundle
import com.example.hyperlocal.BaseActivity
import com.example.hyperlocal.MainActivity
import com.example.hyperlocal.R

class ManagePermissionActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_permission)

        assert(supportActionBar != null)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Manage Permission"

        toast("Manage Location Permission")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAndStart(MainActivity::class.java)
    }
}