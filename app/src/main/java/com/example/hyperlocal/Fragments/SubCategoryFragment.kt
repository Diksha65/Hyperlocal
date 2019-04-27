package com.example.hyperlocal.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hyperlocal.Model.SubCategory
import com.example.hyperlocal.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import kotlinx.android.synthetic.main.subcategory_item_view.view.*


class SubCategoryFragment : Fragment() {

    private var categoryName : String? = null
    val firestore = FirebaseFirestore.getInstance()

    companion object {
        @JvmStatic
        fun newInstance(tag : String) =
            SubCategoryFragment().apply {
                arguments = Bundle().apply { putString("categoryName", tag) }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryName = arguments?.getString("categoryName")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firestoreQuery = firestore.collection("subcateogry")
            .whereEqualTo("category.name", categoryName)

        setupFirestoreRecyclerView(firestoreQuery)
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
        productCategoryRecyclerView.layoutManager = LinearLayoutManager(context)
        productCategoryRecyclerView.adapter = firestoreRecyclerAdapter
    }


    inner class SubCategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(subCategory: SubCategory) {
            itemView.apply {
                subcategory_name.text = subCategory.name
                subcategory_name.setOnClickListener {
                    Log.e("SubCategory", subCategory.name + " is clicked")
                    Log.e("Subcategorymodel", subCategory.category["name"])
                    loadFragment(ProductFragment.newInstance(categoryName!!, subCategory.name))
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, fragment, this.javaClass.simpleName)
            ?.addToBackStack(this.javaClass.simpleName)
            ?.commit() ?: Log.e("Category","Null Fragment Manager")
    }
}