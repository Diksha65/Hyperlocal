package com.example.hyperlocal

import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_navigation_drawer.*
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var auth: FirebaseAuth

    inner class ProductCategory (val name : String,@DrawableRes val imageID : Int)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_drawer)
        setSupportActionBar(toolbar)

        auth = FirebaseAuth.getInstance()

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    inner class ProductCategoryAdapter(val categorylist: ArrayList<ProductCategory>) : RecyclerView.Adapter<ProductCategoryAdapter.ProductCategoryHolder>() {

        override fun getItemCount() = categorylist.size

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProductCategoryHolder {
            //return ProductCategoryHolder( p0.inflate(R.layout.grid_item_view))
        }

        override fun onBindViewHolder(p0: ProductCategoryHolder, p1: Int) {
            p0.bindItems(categorylist[p1])
        }

        inner class ProductCategoryHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
            fun bindItems(category : ProductCategory) {
                itemView.apply {

                }
            }
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
        // Handle navigation view item cliks here.
        when (item.itemId) {
            R.id.nav_location -> {
                // Handle the camera action
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
}