package com.example.hyperlocal

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.*
import com.bumptech.glide.Glide
import com.example.hyperlocal.base.BaseActivity
import com.example.hyperlocal.extensions.Firebase.auth
import com.example.hyperlocal.fragments.CategoryFragment
import com.example.hyperlocal.intro.LoginActivity
import com.example.hyperlocal.model.User
import com.example.hyperlocal.navigationactivity.*
import kotlinx.android.synthetic.main.activity_navigation_drawer.*
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.content_navigation_drawer.*
import kotlinx.android.synthetic.main.nav_header_navigation_drawer.*
import android.widget.Toast
import android.app.ProgressDialog
import android.support.v7.widget.SearchView
import android.widget.RelativeLayout
import android.widget.ProgressBar
import com.example.hyperlocal.extensions.Firebase.firestore
import com.example.hyperlocal.fragments.ProductFragment


@SuppressLint("ByteOrderMark")
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val RECORD_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_drawer)
        setSupportActionBar(toolbar)

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                searchData(query)
                return true
            }
        })

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        addFragment(R.id.fragment_container, CategoryFragment())
    }

    fun searchData(query: String?) {
        firestore.collection("products").whereEqualTo("name", query)
            .get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                for (snap in queryDocumentSnapshots) {
                    replaceFragment(
                        R.id.fragment_container,
                        ProductFragment.newInstance(snap["name"].toString())
                    )
                }
            }
            .addOnFailureListener {
                toast("The item searched is currently not present in the database")
                logError(Error("Item not found"))
            }
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
                logDebug("${auth.currentUser!!.email} logging out")
                auth.signOut()
                finishAndStart(LoginActivity::class.java)
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
