package com.example.hyperlocal.model

import com.google.firebase.firestore.model.value.IntegerValue

class Product (
    var ID : String = "",
    var name : String = "",
    var image : String? = "",
    var category : HashMap<String, String> = hashMapOf("id" to "", "name" to ""),
    var subcategory : HashMap<String, String> = hashMapOf("id" to "", "name" to ""),
    var store : HashMap<String, Any>  = hashMapOf("id" to "", "cost" to 0)
)