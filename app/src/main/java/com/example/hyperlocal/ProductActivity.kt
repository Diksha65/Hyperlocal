package com.example.hyperlocal

import android.os.Bundle
import com.example.hyperlocal.base.BaseActivity

class ProductActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        toast("Inside the final activity")
    }
}