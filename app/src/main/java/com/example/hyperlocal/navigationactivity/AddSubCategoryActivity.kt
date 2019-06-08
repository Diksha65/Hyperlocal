package com.example.hyperlocal.navigationactivity

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.bumptech.glide.Glide
import com.example.hyperlocal.base.BaseActivity
import com.example.hyperlocal.MainActivity
import com.example.hyperlocal.R
import com.example.hyperlocal.extensions.Firebase.storage
import com.example.hyperlocal.model.SubCategory
import kotlinx.android.synthetic.main.activity_add_subcategory.*
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File
import java.util.*
import android.view.View
import android.widget.AdapterView
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
            if(!isPermissionGranted(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                requestSinglePermission(this, this, Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                EasyImage.openGallery(this, 0)
            }
        }

        add_subcategory_button.setOnClickListener {
            if(TextUtils.isEmpty(subcategory_name_value.text)) {
                subcategory_name_value.error = "Category name is required"
            } else {
                uploadToFirebase()
            }
            logDebug("Checkingg",
                "${subcategory.ID}, ${subcategory.name}, ${subcategory.image}, ${subcategory.category}")

        }
    }

    private fun uploadToFirebase() {
        val file = Uri.fromFile(selectedImage)
        subcategory.image = file.lastPathSegment

        storage.child("category/${subcategory.image}")
            .putFile(file)
            .addOnSuccessListener {
                toast("Image successfully uploaded")
                subCategoryCollection.document(subcategory.ID)
                    .set(subcategory)
                    .addOnSuccessListener {
                        toast("Data successfully uploaded to cloud")
                        /**
                         * TODO
                         *    :show progress dialog here
                         */
                        onBackPressed()
                    }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, object : DefaultCallback() {
            override fun onImagePickerError(e: Exception?, source: EasyImage.ImageSource?, type: Int) {
                //Some error handling
            }

            override fun onImagesPicked(imagesFiles: List<File>, source: EasyImage.ImageSource, type: Int) {
                //Handle the images
                if(imagesFiles.size == 1)
                    selectedImage = imagesFiles[0]
                Glide.with(this@AddSubCategoryActivity).load(selectedImage).into(subcategory_image_value)
            }
        })
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

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

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

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                logDebug("Nothing Selected")
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                onItemSelected(parent?.getItemAtPosition(position) as String)
            }
        }
    }
}