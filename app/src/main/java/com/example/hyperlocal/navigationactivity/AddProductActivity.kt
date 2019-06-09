package com.example.hyperlocal.navigationactivity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.widget.Spinner
import com.example.hyperlocal.base.BaseActivity
import com.example.hyperlocal.MainActivity
import com.example.hyperlocal.R
import com.example.hyperlocal.extensions.*
import com.example.hyperlocal.model.Product
import kotlinx.android.synthetic.main.activity_add_product.*
import java.io.File
import java.util.*

class AddProductActivity : BaseActivity() {

    private lateinit var selectedImage : File
    private var product = Product()

    private var listOfCategoryMap : HashMap<String, String> = HashMap()
    private var listOfSubCategoryMap : HashMap<String, String> = HashMap()
    private var listOfStoreMap : HashMap<String, String> = HashMap()

    private val categories = ArrayList<String>()
    private val subcategories = ArrayList<String>()
    private val stores = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        assert(supportActionBar != null)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "New Product"

        product.ID = UUID.randomUUID().toString()
        product_name_value.onChange { text -> product.name = text }
        product_cost_value.onChange { text -> product.store["cost"] = text}

        setUpCategorySpinner(category_type_value) { selected ->
            product.category["name"] = selected
            for(items in listOfCategoryMap) {
                if(items.value == selected) {
                    product.category["id"] = items.key
                    subcategory_type_value.isEnabled = true
                    break
                }
            }
        }
        setUpSubCategorySpinner(subcategory_type_value) { selected ->
            product.subcategory["name"] = selected
            for(items in listOfSubCategoryMap) {
                if(items.value == selected) {
                    product.subcategory["id"] = items.key
                    break
                }
            }
        }
        setUpStoreSpinner(store_value) { selected ->
            for(items in listOfStoreMap) {
                if (items.value == selected) {
                    product.store["id"] = items.key
                    break
                }
            }
        }

        upload.setOnClickListener {
            openGallery(this, this)
        }

        add_product_button.setOnClickListener {
            if(TextUtils.isEmpty(product_name_value.text)) {
                product_name_value.error = "Product name is required"
            } else if (TextUtils.isEmpty(product_cost_value.text)){
                product_cost_value.error = "Product Cost is required"
            } else {
                uploadToFirebase()
            }
        }

    }

    private fun uploadToFirebase() {
        uploadImage(selectedImage, "product")
            .addOnSuccessListener {
                product.image = Uri.fromFile(selectedImage).lastPathSegment

                productCollection.document(product.ID)
                    .set(product)
                    .addOnSuccessListener {
                        /**TODO
                         *    :show progress dialog here
                         */
                        onBackPressed()
                    }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        selectedImage = galleryResult(requestCode, resultCode, data)
        /**
         * TODO
         *  :Add multiple images functionality
         */
        displayImage(selectedImage, product_image_value)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAndStart(MainActivity::class.java)
    }

    fun setUpCategorySpinner(spinner : Spinner, onItemSelected : (String) -> Unit) {

        val adapter = spinner.setAdapter(this, categories)
        categoryCollection
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        listOfCategoryMap[document["id"].toString()] = document["name"].toString()
                        categories.add(document["name"].toString())
                    }
                    adapter.notifyDataSetChanged()
                }
            }
        spinner.selectSpinnerElement(onItemSelected)
    }

    fun setUpSubCategorySpinner(spinner : Spinner, onItemSelected : (String) -> Unit) {
        val adapter = spinner.setAdapter(this, subcategories)
        subCategoryCollection
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        listOfSubCategoryMap[document["id"].toString()] = document["name"].toString()
                        subcategories.add(document["name"].toString())
                    }
                    adapter.notifyDataSetChanged()
                }
            }
        spinner.selectSpinnerElement(onItemSelected)
    }

    fun setUpStoreSpinner(spinner : Spinner, onItemSelected : (String) -> Unit) {
        val adapter = spinner.setAdapter(this, stores)
        storeCollection
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        listOfStoreMap[document["id"].toString()] = document["name"].toString()
                        stores.add(document["name"].toString())
                    }
                    adapter.notifyDataSetChanged()
                }
            }
        spinner.selectSpinnerElement(onItemSelected)
    }
}