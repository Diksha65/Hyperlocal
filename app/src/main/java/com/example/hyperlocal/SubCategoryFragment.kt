package com.example.hyperlocal

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.sql.Array

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
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firestoreQuery = firestore.collection("categories").document(categoryName!!)
        Log.e("Subcategory", firestoreQuery.toString())

        //setupFirestoreRecyclerView(firestoreQuery)
        //productCategoryRecyclerView.layoutManager = LinearLayoutManager(context)
        //productCategoryRecyclerView.setHasFixedSize(true)
        //productCategoryRecyclerView.adapter = CategoryAdapter(categories)
    }

    /*
    inner class SubCategoryAdapter(val subcategorylist: ArrayList<SubProductCategory>) : RecyclerView.Adapter<SubCategoryAdapter.SubCategoryHolder>() {

        override fun getItemCount() = subcategorylist.size

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SubCategoryHolder {
            return SubCategoryHolder(LayoutInflater.from(p0.context).inflate(R.layout.category_item_view, p0, false))
        }

        override fun onBindViewHolder(p0: SubCategoryHolder, p1: Int) {
            p0.bindItems(subcategorylist[p1])
        }

        inner class SubCategoryHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
            fun bindItems(category : SubProductCategory) {
                itemView.apply {
                    Glide.with(itemView).load(category.imageID).into(itemView.category_image)
                    category_name.text = category.name

                    arrayOf(category_image, category_name).forEach {
                        it.setOnClickListener {
                            Log.e("Griditem", category.name)
                        }
                    }
                }
            }
        }
    }
    */
}