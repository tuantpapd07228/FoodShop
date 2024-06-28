package com.example.asmand103.response

data class Cart(
    val __v: Int,
    val _id: String,
//    val fruit:String,
    val createdAt: String,
    val quantity: Int,
    val description: String,
    val type: String,
    val fruit: String,
    val images: List<String>,
    val name: String,
    val price: Int,
    val updatedAt: String
)