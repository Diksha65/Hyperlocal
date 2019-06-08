package com.example.hyperlocal.navigationactivity

import android.os.Bundle
import android.text.TextUtils
import com.example.hyperlocal.base.BaseActivity
import com.example.hyperlocal.MainActivity
import com.example.hyperlocal.R
import com.example.hyperlocal.extensions.onChange
import com.example.hyperlocal.extensions.storeCollection
import com.example.hyperlocal.model.Store
import kotlinx.android.synthetic.main.activity_add_store.*
import java.util.*

class AddStoreActivity : BaseActivity() {

    private var store = Store()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_store)

        assert(supportActionBar != null)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "New Store"

        store.ID = UUID.randomUUID().toString()
        store_name_value.onChange { text -> store.name = text }
        store_address_value.onChange { text -> store.location = text}
        store_phoneno_value.onChange { text -> store.phoneno = text}

        add_store.setOnClickListener {
            if(TextUtils.isEmpty(store_name_value.text)) {
                store_name_value.error = "Product name is required"
            } else if (TextUtils.isEmpty(store_address_value.text)){
                store_address_value.error = "Product Cost is required"
            } else if (TextUtils.isEmpty(store_phoneno_value.text)) {
                store_phoneno_value.error = "Product Cost is required"
            } else {
                uploadToFirebase()
            }
        }
    }

    private fun uploadToFirebase() {
        storeCollection.document(store.ID)
            .set(store)
            .addOnSuccessListener {
                /**TODO
                 *    :show progress dialog here
                 */
                onBackPressed()
            }
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