package com.example.hyperlocal.model

class Product (
    var ID : String = "",
    var name : String = "",
    var image : ArrayList<String> = arrayListOf(),
    var category : Map<String, String> = mapOf("id" to "", "name" to ""),
    var subcategory : Map<String, String> = mapOf("id" to "", "name" to ""),
    var store : Map<String, String> = mapOf("id" to "", "name" to "", "location" to "", "cost" to "0.0")
)