package com.example.asmand103.response

data class Order(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val date: String,
    val fruit: Fruit,
    val order_code: String,
    val quantity: Int,
    val updatedAt: String
)