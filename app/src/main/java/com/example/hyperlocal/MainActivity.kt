package com.example.hyperlocal

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.*
import com.example.hyperlocal.base.BaseActivity
import com.example.hyperlocal.extensions.Firebase.auth
import com.example.hyperlocal.fragments.CategoryFragment
import com.example.hyperlocal.navigationactivity.*
import kotlinx.android.synthetic.main.activity_navigation_drawer.*
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_drawer)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        addFragment(R.id.fragment_container, CategoryFragment())
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.navigation_drawer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_permission -> {
                finishAndStart(ManagePermissionActivity::class.java)
            }

            R.id.nav_category -> {
                openNavigationActivity(AddCategoryActivity())
            }

            R.id.nav_subcategory -> {
                openNavigationActivity(AddSubCategoryActivity())
            }

            R.id.nav_product -> {
                openNavigationActivity(AddProductActivity())
            }

            R.id.nav_store -> {
                openNavigationActivity(AddStoreActivity())
            }

            R.id.nav_logout -> {

            }

            R.id.nav_share -> {

            }

            R.id.nav_rate -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun openNavigationActivity(activity : Activity) {
        if(auth.currentUser?.email == "dikshaagarwal74@gmail.com") {
            snackbar("Opening Navigation Activity")
            finishAndStart(activity::class.java)
        } else {
            snackbar("You are not allowed to add categories to the database")
        }
    }
}
