package com.example.hyperlocal.navigationactivity

import android.os.Bundle
import android.os.PersistableBundle
import com.example.hyperlocal.BaseActivity
import com.example.hyperlocal.MainActivity
import com.example.hyperlocal.R
import kotlinx.android.synthetic.main.sample.*

class AddSubCategoryActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_subcategory)

        assert(supportActionBar != null)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "New Category"

        toast("Adding Sub Category")
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