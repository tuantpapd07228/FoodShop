package com.example.asmand103.request

data class Item(
    val category: Category,
    val code: String,
    val height: Int,
    val length: Int,
    var name: String,
    var price: Int,
    val quantity: Int,
    val weight: Int,
    val width: Int
)