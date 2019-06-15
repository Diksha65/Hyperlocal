package com.example.hyperlocal

import android.os.Bundle
import com.example.hyperlocal.base.BaseActivity
import android.content.Intent
import android.net.Uri
import com.bumptech.glide.Glide
import com.example.hyperlocal.extensions.Firebase
import com.example.hyperlocal.extensions.productCollection
import com.example.hyperlocal.extensions.storeCollection
import com.example.hyperlocal.model.Product
import com.example.hyperlocal.model.Store
import kotlinx.android.synthetic.main.activity_product.*


class ProductActivity : BaseActivity() {

    private var PRODUCTKEY = "ProductKey"
    private var product = Product()
    private var store = Store()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val productid = intent.getStringExtra(PRODUCTKEY)

        productCollection.document(productid)
            .get()
            .addOnSuccessListener {productSnapshot ->
                product = productSnapshot.toObject(Product::class.java)!!

                storeCollection.document(product.store["id"].toString())
                    .get()
                    .addOnSuccessListener { storeSnapshot ->
                        store =  storeSnapshot.toObject(Store::class.java)!!
                        updateViews(product, store)
                    }
            }
        onLocationClick()
    }

    private fun updateViews(product : Product, store : Store) {
        prod_name.text = product.name
        Glide.with(this)
            .load(Firebase.storage.child("product/${product.image}"))
            .into(prod_image)
        prod_store_name.text = store.name
        prod_store_loc.text = store.location
        prod_cost_value.text = product.store["cost"].toString()
    }

    private fun onLocationClick() {
        arrayOf(
            prod_store_loc_icon,
            prod_store_loc
        ).forEach {
            it.setOnClickListener {
                val uri = "http://maps.google.co.in/maps?q=${store.location}"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                startActivity(intent)
            }
        }
    }
}