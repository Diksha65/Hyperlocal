package com.example.hyperlocal.navigationactivity

import android.os.Bundle
import com.example.hyperlocal.BaseActivity
import com.example.hyperlocal.MainActivity
import com.example.hyperlocal.R

class AddCategoryActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        assert(supportActionBar != null)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "New Category"

        logDebug("New Category")
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