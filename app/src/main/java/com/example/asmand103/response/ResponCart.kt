package com.example.asmand103.response

data class ResponCart(
    val Carts: List<Cart>,
    val message: String,
    val status: Int
)