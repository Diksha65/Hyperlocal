package com.example.hyperlocal.fragments

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hyperlocal.base.BaseFragment
import com.example.hyperlocal.extensions.categoryCollection
import com.example.hyperlocal.model.Category
import com.example.hyperlocal.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.category_item_view.view.*
import kotlinx.android.synthetic.main.fragment_recyclerview.*

class CategoryFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFirestoreRecyclerView(categoryCollection)
    }

    private fun setupFirestoreRecyclerView (query: Query) {
        val firestoreOptions = FirestoreRecyclerOptions.Builder<Category>()
            .setLifecycleOwner(this)
            .setQuery(query, Category::class.java)
            .build()

        val firestoreRecyclerAdapter = object : FirestoreRecyclerAdapter<Category, CategoryHolder>(firestoreOptions) {
            override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CategoryHolder {
                return CategoryHolder(
                    LayoutInflater.from(p0.context).inflate(R.layout.category_item_view, p0, false)
                )
            }
            override fun onBindViewHolder(holder: CategoryHolder, position: Int, model: Category) {
                holder.bindItems(model)
            }
        }
        productCategoryRecyclerView.layoutManager = GridLayoutManager(context, 2)
        productCategoryRecyclerView.adapter = firestoreRecyclerAdapter
    }

    inner class CategoryHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(category : Category) {
            itemView.apply {
                //Glide.with(itemView).load(category.image).into(itemView.category_image)
                category_name.text = category.name
                arrayOf(category_image, category_name).forEach {
                    it.setOnClickListener {
                        logDebug(category.name)
                        replaceFragment(
                            R.id.fragment_container,
                            SubCategoryFragment.newInstance(category.name)
                        )
                    }
                }
            }
        }
    }
}