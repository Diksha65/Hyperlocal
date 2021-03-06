package com.example.hyperlocal.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.hyperlocal.base.BaseFragment
import com.example.hyperlocal.extensions.subCategoryCollection
import com.example.hyperlocal.model.SubCategory
import com.example.hyperlocal.R
import com.example.hyperlocal.extensions.Firebase
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import kotlinx.android.synthetic.main.subcategory_item_view.view.*

class SubCategoryFragment : BaseFragment() {

    private var categoryId : String? = null

    companion object {
        @JvmStatic
        fun newInstance(tag : String) =
            SubCategoryFragment().apply {
                arguments = Bundle().apply { putString("categoryId", tag) }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryId = arguments?.getString("categoryId")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFirestoreRecyclerView(subCategoryCollection
            .whereEqualTo("category.id", categoryId))
    }

    private fun setupFirestoreRecyclerView (query: Query) {
        val firestoreOptions = FirestoreRecyclerOptions.Builder<SubCategory>()
            .setLifecycleOwner(this)
            .setQuery(query, SubCategory::class.java)
            .build()

        val firestoreRecyclerAdapter = object : FirestoreRecyclerAdapter<SubCategory, SubCategoryHolder>(firestoreOptions) {
            override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SubCategoryHolder {
                return SubCategoryHolder(
                    LayoutInflater.from(p0.context).inflate(R.layout.subcategory_item_view, p0, false)
                )
            }
            override fun onBindViewHolder(holder: SubCategoryHolder, position: Int, model: SubCategory) {
                holder.bindItems(model)
            }
        }
        productCategoryRecyclerView.layoutManager = LinearLayoutManager(this.context)
        productCategoryRecyclerView.adapter = firestoreRecyclerAdapter
    }


    inner class SubCategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(subCategory: SubCategory) {
            itemView.apply {
                subcategory_name.text = subCategory.name

                Glide.with(base)
                    .load(Firebase.storage.child("subcategory/${subCategory.image}"))
                    .into(itemView.subcategory_image)

                arrayOf(subcategory_image, subcategory_name).forEach {
                    it.setOnClickListener {
                        replaceFragment(
                            R.id.fragment_container,
                            ProductFragment.newInstance(subCategory.ID)
                        )
                    }
                }
            }
        }
    }
}