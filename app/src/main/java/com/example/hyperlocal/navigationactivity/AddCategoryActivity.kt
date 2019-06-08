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
import com.example.hyperlocal.extensions.Firebase.storage
import com.example.hyperlocal.extensions.categoryCollection
import com.example.hyperlocal.extensions.isPermissionGranted
import com.example.hyperlocal.extensions.onChange
import com.example.hyperlocal.extensions.requestSinglePermission
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

        upload_image.setOnClickListener {
            if(!isPermissionGranted(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                requestSinglePermission(this, this, Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                EasyImage.openGallery(this, 0)
            }
        }

        add_category_button.setOnClickListener {
            if(TextUtils.isEmpty(category_name_value.text)) {
                category_name_value.error = "Category name is required"
            } else {
                uploadToFirebase()
            }
        }
    }

    private fun uploadToFirebase() {
        val file = Uri.fromFile(selectedImage)
        category.image = file.lastPathSegment

        storage.child("category/${category.image}")
            .putFile(file)
            .addOnSuccessListener {
                toast("Image successfully uploaded")
                categoryCollection.document(category.ID)
                    .set(category)
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
                Glide.with(this@AddCategoryActivity).load(selectedImage).into(category_image_value)
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