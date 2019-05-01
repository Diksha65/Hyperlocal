package com.example.hyperlocal.extensions

import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestoreException
import android.support.constraint.solver.widgets.ResolutionNode.REMOVED
import com.google.firebase.firestore.DocumentSnapshot
import com.firebase.ui.common.ChangeEventType
import com.firebase.ui.firestore.ObservableSnapshotArray
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import java.nio.file.Files.getOwner
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import android.arch.lifecycle.LifecycleObserver
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hyperlocal.R
import com.firebase.ui.firestore.ChangeEventListener
import com.firebase.ui.firestore.FirestoreArray
import com.google.firebase.firestore.Query


object Firebase {
    /**
     * Custom getters so that we don't store a reference to context
     * (FirebaseFirestore.getInstance() has reference to context)
     */
    val storage : StorageReference
        get() = FirebaseStorage.getInstance().reference

    val firestore : FirebaseFirestore
        get() = FirebaseFirestore.getInstance()

    val auth : FirebaseAuth
        get() = FirebaseAuth.getInstance()
}

/**
 * All the collections are stored in a variable
 */
val usersCollection         = Firebase.firestore.collection("users")
val categoryCollection      = Firebase.firestore.collection("category")
val subCategoryCollection   = Firebase.firestore.collection("subcateogry")
val productCollection       = Firebase.firestore.collection("products")
val storesCollection        = Firebase.firestore.collection("stores")

/**
 * FirebaseRecyclerAdapter
 */

/*
private fun<T> setupFirestoreRecyclerView (
        query: Query,
        model : Class<T>,
        VH : RecyclerView.ViewHolder,
        layoutView : Int) {

    val firestoreOptions = FirestoreRecyclerOptions.Builder<T>()
        .setQuery(query, model)
        .build()

    val firestoreRecyclerAdapter = object : FirestoreRecyclerAdapter<T, viewHolder>(firestoreOptions) {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): viewHolder {
            return viewHolder (
                LayoutInflater.from(p0.context).inflate(layoutView, p0, false)
            )
        }
        override fun onBindViewHolder(holder: viewHolder, position: Int, model: T) {
            holder.bindItems(model)
        }
    }
}

class viewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    fun bindItems(model : Class<T>)
    protected abstract fun bindItems()
}

abstract class FirestoreRecyclerAdapter<T, VH : RecyclerView.ViewHolder>
    (options: FirestoreRecyclerOptions<T>) : RecyclerView.Adapter<VH>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): VH {
        return onCreateViewHolder(p0, p1)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        onBindViewHolder(holder, position, getItem(position))
    }

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

    protected abstract fun onBindViewHolder(holder: VH, position: Int, model: T)
    protected abstract fun onCreateViewHolder(p0: ViewGroup)
}
*/
