package com.example.hyperlocal.model

class Product (
    var ID : String = "",
    var name : String = "",
    var image : String? = "",
    var category : HashMap<String, String> = hashMapOf("id" to "", "name" to ""),
    var subcategory : HashMap<String, String> = hashMapOf("id" to "", "name" to ""),
    var store : HashMap<String, String>  = hashMapOf("id" to "", "cost" to "0")
)