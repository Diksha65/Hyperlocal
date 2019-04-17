package com.example.hyperlocal

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class SubCategoryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //productCategoryRecyclerView.layoutManager = LinearLayoutManager(context)
        //productCategoryRecyclerView.setHasFixedSize(true)
        //productCategoryRecyclerView.adapter = CategoryAdapter(categories)
    }
}