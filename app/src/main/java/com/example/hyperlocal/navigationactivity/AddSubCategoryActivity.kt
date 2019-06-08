package com.example.hyperlocal.navigationactivity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.widget.Spinner
import com.example.hyperlocal.base.BaseActivity
import com.example.hyperlocal.MainActivity
import com.example.hyperlocal.R
import com.example.hyperlocal.model.SubCategory
import kotlinx.android.synthetic.main.activity_add_subcategory.*
import java.io.File
import java.util.*
import com.example.hyperlocal.extensions.*
import kotlin.collections.HashMap

class AddSubCategoryActivity : BaseActivity() {

    private lateinit var selectedImage : File
    private var subcategory = SubCategory()
    private var listOfCategoryMap : HashMap<String, String> = HashMap()
    private val categories = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_subcategory)

        assert(supportActionBar != null)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "New Sub-Category"

        subcategory.ID = UUID.randomUUID().toString()
        subcategory_name_value.onChange { text -> subcategory.name = text }

        setUpSpinner(category_type_value) { selected ->

            subcategory.category["name"] = selected
            for(items in listOfCategoryMap) {
                if(items.value == selected) {
                    subcategory.category["id"] = items.key
                    break
                }
            }
        }

        upload_button.setOnClickListener {
            openGallery(this, this)
        }

        add_subcategory_button.setOnClickListener {
            if(TextUtils.isEmpty(subcategory_name_value.text)) {
                subcategory_name_value.error = "SubCategory name is required"
            } else {
                uploadToFirebase()
            }
        }
    }

    private fun uploadToFirebase() {
        uploadImage(selectedImage)
            .addOnSuccessListener {
                subcategory.image = Uri.fromFile(selectedImage).lastPathSegment

                subCategoryCollection.document(subcategory.ID)
                    .set(subcategory)
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
        displayImage(selectedImage, subcategory_image_value)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAndStart(MainActivity::class.java)
    }

    fun setUpSpinner(spinner : Spinner, onItemSelected : (String) -> Unit) {

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
}