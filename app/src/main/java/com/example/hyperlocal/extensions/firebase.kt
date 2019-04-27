package com.example.hyperlocal.extensions

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

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

