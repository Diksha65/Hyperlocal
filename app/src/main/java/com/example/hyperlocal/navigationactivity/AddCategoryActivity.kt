package com.example.hyperlocal.navigationactivity

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import com.bumptech.glide.Glide
import com.example.hyperlocal.MainActivity
import com.example.hyperlocal.R
import com.example.hyperlocal.base.BaseActivity
import com.example.hyperlocal.extensions.*
import com.example.hyperlocal.extensions.Firebase.storage
import com.example.hyperlocal.model.Category
import kotlinx.android.synthetic.main.activity_add_category.*
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File
import java.util.*


class AddCategoryActivity : BaseActivity() {

    private lateinit var selectedImage : File
    private var category = Category()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        assert(supportActionBar != null)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "New Category"

        category.ID = UUID.randomUUID().toString()
        category_name_value.onChange { text -> category.name = text }

        upload_image.setOnClickListener { openGallery(this, this) }

        add_category_button.setOnClickListener {
            if(TextUtils.isEmpty(category_name_value.text)) {
                category_name_value.error = "Category name is required"
            } else {
                uploadToFirebase()
            }
        }
    }

    private fun uploadToFirebase() {
        uploadImage(selectedImage, "category")
            .addOnSuccessListener {
                category.image = Uri.fromFile(selectedImage).lastPathSegment

                categoryCollection.document(category.ID)
                    .set(category)
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
        displayImage(selectedImage, category_image_value)
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