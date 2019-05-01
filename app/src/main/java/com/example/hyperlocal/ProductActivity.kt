package com.example.hyperlocal

import android.os.Bundle

class ProductActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        toast("Inside the final activity")
    }
}