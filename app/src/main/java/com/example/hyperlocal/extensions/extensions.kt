package com.example.hyperlocal.extensions

import android.R
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.example.hyperlocal.base.BaseActivity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirestoreRegistrar
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File

fun EditText.onChange( onTextChange: (input : String) -> Unit  ) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChange(s.toString())
        }
    })
}

fun BaseActivity.galleryResult(requestCode: Int, resultCode: Int, data: Intent?) : File {

    var selectedImage : File? = null

    EasyImage.handleActivityResult(requestCode, resultCode, data, this, object : DefaultCallback() {
        override fun onImagePickerError(e: Exception?, source: EasyImage.ImageSource?, type: Int) {
            //Some error handling
        }

        override fun onImagesPicked(imagesFiles: List<File>, source: EasyImage.ImageSource, type: Int) {
            //Handle the images
            if(imagesFiles.size == 1) {
                selectedImage = imagesFiles[0]
            }
        }
    })
    return selectedImage!!
}

fun BaseActivity.displayImage(imageFile : File, imageView : ImageView) {
    Glide.with(this).load(imageFile).into(imageView)
}

fun Spinner.selectSpinnerElement(onItemSelected : (String) -> Unit) {

    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            logDebug("Nothing Selected")
        }
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            onItemSelected(parent?.getItemAtPosition(position) as String)
        }
    }
}

fun Spinner.setAdapter(context: Context, list : ArrayList<String>) : ArrayAdapter<String> {
    val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, list)
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    this.adapter = adapter
    return adapter
}
