package com.example.hyperlocal

import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.category_item_view.view.*
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryFragment : Fragment() {

    inner class ProductCategory (val name : String,@DrawableRes val imageID : Int)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productCategoryRecyclerView.layoutManager = GridLayoutManager(context, 2)
        productCategoryRecyclerView.setHasFixedSize(true)
        productCategoryRecyclerView.adapter = CategoryAdapter(categories)
    }

    private val categories = arrayListOf(
        ProductCategory("Clothing", R.drawable.clothing),
        ProductCategory("Accessories", R.drawable.accessories),
        ProductCategory("Books", R.drawable.books),
        ProductCategory("Groceries", R.drawable.groceries),
        ProductCategory("Electronics", R.drawable.electronics),
        ProductCategory("Sports", R.drawable.sports)
    )

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
                            Log.e("Griditem", category.name)
                        }
                    }
                }
            }
        }
    }
}