package com.example.hyperlocal.navigationactivity

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import com.bumptech.glide.Glide
import com.example.hyperlocal.base.BaseActivity
import com.example.hyperlocal.MainActivity
import com.example.hyperlocal.R
import com.example.hyperlocal.extensions.Firebase.storage
import com.example.hyperlocal.extensions.isPermissionGranted
import com.example.hyperlocal.extensions.onChange
import com.example.hyperlocal.extensions.requestSinglePermission
import com.example.hyperlocal.extensions.subCategoryCollection
import com.example.hyperlocal.model.SubCategory
import kotlinx.android.synthetic.main.activity_add_subcategory.*
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File
import java.util.*

class AddSubCategoryActivity : BaseActivity() {

    private lateinit var selectedImage : File
    private var subcategory = SubCategory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_subcategory)

        assert(supportActionBar != null)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "New Sub-Category"

        subcategory.ID = UUID.randomUUID().toString()
        subcategory_name_value.onChange { text -> subcategory.name = text }

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
}