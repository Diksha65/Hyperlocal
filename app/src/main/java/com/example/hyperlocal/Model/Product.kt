package com.example.hyperlocal.Model

class Product (
    var ID : String = "",
    var name : String = "",
    var image : String = "",
    var category : Map<String, String> = mapOf("id" to "", "name" to ""),
    var subcategory : Map<String, String> = mapOf("id" to "", "name" to ""),
    var store : Map<String, String> = mapOf("id" to "", "name" to "", "location" to "", "cost" to "0.0")
)