package com.example.hyperlocal.fragments

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.hyperlocal.MainActivity
import com.example.hyperlocal.ProductActivity
import com.example.hyperlocal.R
import com.example.hyperlocal.base.BaseFragment
import com.example.hyperlocal.extensions.Firebase
import com.example.hyperlocal.extensions.Firebase.firestore
import com.example.hyperlocal.extensions.isPermissionGranted
import com.example.hyperlocal.extensions.productCollection
import com.example.hyperlocal.extensions.storeCollection
import com.example.hyperlocal.model.Product
import com.example.hyperlocal.model.Store
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.product_item_view.*
import kotlinx.android.synthetic.main.product_item_view.view.*
import kotlinx.android.synthetic.main.product_recyclerview.*


class ProductFragment : BaseFragment() {

    private var subCategoryId : String? = null

    companion object {
        @JvmStatic
        fun newInstance(tagSubCategory: String) =
            ProductFragment().apply {
                arguments = Bundle().apply { putString("subCategoryId", tagSubCategory) }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subCategoryId = arguments?.getString("subCategoryId")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.product_recyclerview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sort_by_distance.setOnClickListener {
            if(isPermissionGranted(base, Manifest.permission.ACCESS_FINE_LOCATION))
                toast("Permission there")
            else
                toast("Permission not there")
        }

        /*sort_by_price.setOnClickListener {
            logError(Error("Clicked"))
            toast("Sort by price clicked")
            setupFirestoreRecyclerView(productCollection
                .whereEqualTo("subcategory.id", subCategoryId)
                .orderBy("store.cost", Query.Direction.ASCENDING))
        }*/

        setupFirestoreRecyclerView(productCollection
            .whereEqualTo("subcategory.id", subCategoryId))
    }

    private fun setupFirestoreRecyclerView (query: Query) {
        val firestoreOptions = FirestoreRecyclerOptions.Builder<Product>()
            .setLifecycleOwner(this)
            .setQuery(query, Product::class.java)
            .build()

        val firestoreRecyclerAdapter = object : FirestoreRecyclerAdapter<Product, ProductHolder>(firestoreOptions) {
            override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProductHolder {
                return ProductHolder(
                    LayoutInflater.from(p0.context).inflate(R.layout.product_item_view, p0, false)
                )
            }
            override fun onBindViewHolder(holder: ProductHolder, position: Int, model: Product) {
                holder.bindItems(model)
            }
        }
        productRecyclerView.layoutManager = LinearLayoutManager(context)
        productRecyclerView.adapter = firestoreRecyclerAdapter
    }

    inner class ProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(product: Product) {
            itemView.apply {
                storeCollection.document(product.store["id"].toString())
                    .get()
                    .addOnSuccessListener {documentSnapshot ->
                        val store = documentSnapshot.toObject(Store::class.java)
                        product_store_name.text = store!!.name
                        product_store_location.text = store.location
                    }

                product_cost_value.text = product.store["cost"].toString()

                Glide.with(base)
                    .load(Firebase.storage.child("product/${product.image}"))
                    .into(itemView.product_image)

                arrayOf(
                    product_store_location_icon,
                    product_store_location
                ).forEach {
                    it.setOnClickListener {
                        val uri = "http://maps.google.co.in/maps?q=${product.store["location"]}"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                        startActivity(intent)
                    }
                }

                arrayOf(
                    itemView,
                    product_store_name,
                    product_cost_value,
                    product_image,
                    product_cost
                ).forEach {
                    it.setOnClickListener {
                        (activity as MainActivity).let{
                            val intent = Intent (it, ProductActivity::class.java)
                            logError(Error(intent.toString()))
                            it.startActivity(intent)
                        }
                    }
                }
            }
        }
    }
}