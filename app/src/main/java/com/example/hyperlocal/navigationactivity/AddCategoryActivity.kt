package com.example.hyperlocal.navigationactivity

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.hyperlocal.base.BaseActivity
import com.example.hyperlocal.MainActivity
import com.example.hyperlocal.R
import com.example.hyperlocal.extensions.Firebase.storage
import com.example.hyperlocal.model.Category
import com.example.hyperlocal.extensions.onChange
import kotlinx.android.synthetic.main.activity_add_category.*
import java.io.File
import com.example.hyperlocal.extensions.isPermissionGranted
import com.example.hyperlocal.extensions.requestSinglePermission
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.DefaultCallback


class AddCategoryActivity : BaseActivity() {

    private val RESULT_LOAD_IMAGE = 1
    private var category = Category()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        assert(supportActionBar != null)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "New Category"

        category_name_value.onChange { text -> category.name = text }
        category.ID = category.name

        upload_image.setOnClickListener {
            if(!isPermissionGranted(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                requestSinglePermission(this, this, Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                EasyImage.openGallery(this, 0)
            }
        }

        add_category_button.setOnClickListener {
            logDebug(category.name)
            logDebug(category.image.toString())
            uploadToFirebase()
        }
    }

    private fun uploadToFirebase() {
        val categoryRef = storage.child("category/${category.image}")
        logDebug("${category.name}, ${category.ID}, ${category.image}")
        categoryRef.putFile(Uri.fromFile(category.image))
            .addOnSuccessListener {
                toast("File successfully uploaded")
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
                category.image = imagesFiles[0]
                Glide.with(this@AddCategoryActivity).load(imagesFiles[0]).into(category_image_value)
                logDebug(category.image.toString())
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

}