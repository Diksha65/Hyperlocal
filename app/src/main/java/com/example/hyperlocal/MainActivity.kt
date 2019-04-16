package com.example.hyperlocal

import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_navigation_drawer.*
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.content_navigation_drawer.*
import kotlinx.android.synthetic.main.category_item_view.view.*

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

        productCategoryRecyclerView.layoutManager = GridLayoutManager(this, 2)
        productCategoryRecyclerView.setHasFixedSize(true)
        productCategoryRecyclerView.adapter = CategoryAdapter(categories)
    }

    inner class CategoryAdapter(val categorylist: ArrayList<ProductCategory>) : RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

        override fun getItemCount() = categorylist.size

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CategoryHolder {
            return CategoryHolder(LayoutInflater.from(p0.context).inflate(R.layout.category_item_view, p0, false))
        }

        override fun onBindViewHolder(p0: CategoryHolder, p1: Int) {
            p0.bindItems(categorylist[p1])
        }

        inner class CategoryHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
            fun bindItems(category : ProductCategory) {
                itemView.apply {
                    Glide.with(itemView).load(category.imageID).into(itemView.category_image)
                    category_name.text = category.name

                    arrayOf(category_image, category_name).forEach {
                        it.setOnClickListener {
                            Log.d("Griditem", category_name.toString())
                        }
                    }
                }
            }
        }
    }

    val categories = arrayListOf(
        ProductCategory("Clothing", R.drawable.clothing),
        ProductCategory("Accessories", R.drawable.accessories),
        ProductCategory("Books", R.drawable.books),
        ProductCategory("Groceries", R.drawable.groceries),
        ProductCategory("Electronics", R.drawable.electronics),
        ProductCategory("Sports", R.drawable.sports)
    )

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
