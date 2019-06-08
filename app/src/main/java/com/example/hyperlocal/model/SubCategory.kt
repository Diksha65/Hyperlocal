package com.example.hyperlocal.model

class SubCategory (
    var ID : String = "",
    var name : String = "",
    var image : String? = "",
    var category : HashMap<String, String> = hashMapOf("id" to "", "name" to "")

)