package com.example.asmand103.request

data class RequestCart(
    val description: String,
    val images: List<String>,
    val name: String,
    val fruit: String,
    val price: Int,
    val quantity: Int,
    val type: String
)