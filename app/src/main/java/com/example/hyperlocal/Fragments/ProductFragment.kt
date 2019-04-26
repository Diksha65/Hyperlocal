package com.example.hyperlocal.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hyperlocal.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.sample.*

class ProductFragment : Fragment() {

    private var categoryName : String? = null
    private var subCategoryName : String? = null
    val firestore = FirebaseFirestore.getInstance()

    companion object {
        @JvmStatic
        fun newInstance(tagCategory: String, tagSubCategory: String) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putString("categoryName", tagCategory)
                    putString("subCategoryName", tagSubCategory)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryName = arguments?.getString("categoryName")
        subCategoryName = arguments?.getString("subCategoryName")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.product_recyclerview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(context, subCategoryName, Toast.LENGTH_SHORT).show()
    }
}